import models.Autor;
import models.Laenutus;
import models.Lugeja;
import models.Teos;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.*;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by raydnoper on 19/02/17.
 */
public class RaamatukoguService {

    Logger log = LoggerFactory.getLogger(getClass());


    private void restartConnection() {
        if (!Base.hasConnection()) {
            Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost/raamatukogu", "raamatukogu", "raamatukogu");
        }
    }

    public boolean testConnection() {
        return Base.hasConnection();
    }

    public RaamatukoguService() {
        log.info("RaamatukoguService startis...");
        restartConnection();

    }

    public String getKataloog(String fTeos, String fAasta, String fAutor) throws Exception {
        int nAasta = 0;
        if (fAasta != null && !fAasta.isEmpty())
            nAasta = Integer.parseInt(fAasta);

        restartConnection();

        JsonObjectBuilder kataloog = Json.createObjectBuilder();
        kataloog.add(
                    "filtrid",
                    Json.createArrayBuilder()
                            .add(Json.createObjectBuilder().add("teos", fTeos == null ? "" : fTeos))
                            .add(Json.createObjectBuilder().add("aasta", fAasta == null ? "" : fAasta))
                            .add(Json.createObjectBuilder().add("autor", fAutor == null ? "" : fAutor)));

        List<Object> params = new ArrayList();
        String query = "";

        // Ehitame dynaamiliselt p2ringu
        if (nAasta != 0) {
            query +=  " AND teosed.aasta=?";
            params.add(nAasta);
        }
        if (fTeos != null) {
            query += " AND teosed.pealkiri ILIKE ?";
            params.add("%" + fTeos + "%");
        }
        if (fAutor != null) {
            query += " AND autorid.nimi ILIKE ?";
            params.add("%" + fAutor + "%");
        }

        List<Teos> list = Teos.findBySQL("SELECT teosed.*, autorid.nimi FROM teosed " +
                " LEFT JOIN autor_teos ON teosed.id=autor_teos.teos_id " +
                " LEFT JOIN autorid ON autor_teos.autor_id=autorid.id " +
                " WHERE 1=1 " + query, params.toArray());

        JsonArrayBuilder teosed = Json.createArrayBuilder();

        if (list != null)
            for (Teos teos : list) {
                StringBuilder sb = new StringBuilder();
                for (Autor a : teos.getAll(Autor.class)) {
                    if (sb.length() > 0)
                        sb.append(", ");
                    sb.append(a.toString());
                }

                teosed.add(Json.createObjectBuilder()
                        .add("id", (int) teos.getInteger("id"))
                        .add("teos", (String) teos.getString("pealkiri"))
                        .add("aasta", (int) teos.getInteger("aasta"))
                        .add("autorid", sb.toString()));
            }

        kataloog.add("teosed", teosed);

        return kataloog.build().toString();
    }


    String laenuta(String lugejaNimi, String teosID) throws Exception {

        restartConnection();

        Lugeja lugeja = Lugeja.first("nimi=?", lugejaNimi);

        if (lugeja == null)
            throw new Exception("Sellist lugejat ei eksisteeri");

        Teos teos = Teos.first("id=?", Integer.parseInt(teosID));

        if (teos == null)
            throw new Exception("Sellist teost ei eksisteeri");

        Laenutus laenutus = Laenutus.first("teos_id = ? AND lopp IS NULL", teos.getInteger("id"));

        if (laenutus != null) {
            throw new Exception("Teos välja laenutatud kasutaja " + laenutus.getInteger("lugeja_id") + " poolt");
        }

        laenutus = new Laenutus();

        laenutus.setInteger("lugeja_id", lugeja.getInteger("id"));
        laenutus.setInteger("teos_id", teos.getInteger("id"));
        laenutus.setDate("algus", Date.from(Instant.now()));

        if (!laenutus.save()) {
            throw new Exception("ei õnnestunud laenutada : " + laenutus.errors());
        }

        return "OK";
    }

    String tagasta(String lugejaNimi, String teosID) throws Exception {

        restartConnection();

        Lugeja lugeja = Lugeja.first("nimi=?", lugejaNimi);

        if (lugeja == null)
            throw new Exception("Sellist lugejat ei eksisteeri");

        Laenutus laenutus = Laenutus.first("teos_id = ? AND lugeja_id = ? AND lopp IS NULL", Integer.parseInt(teosID), lugeja.getInteger("id"));

        if (laenutus == null)
            throw new Exception("Raamat ei ole sellele lugejale laenutatud");

        laenutus.setDate("lopp", Date.from(Instant.now()));

        if (!laenutus.save()) {
            throw new Exception("ei õnnestunud tagastada : " + laenutus.errors());
        }

        return "OK";
    }

    String getLaenutused(String lugejaNimi) throws Exception {

        restartConnection();

        Lugeja lugeja = Lugeja.first("nimi=?", lugejaNimi);

        if (lugeja == null) {
            // tagasta tyhi laenutuste loetelu
            return Json.createObjectBuilder().add("teosed",Json.createArrayBuilder()).build().toString();
        }

        List<Laenutus> list = Laenutus.where("lugeja_id = ?", lugeja.getInteger("id"));

        JsonArrayBuilder teosed = Json.createArrayBuilder();

        if (list != null)
            for (Laenutus laenutus : list) {

                Teos teos = Teos.first("id = ?",laenutus.getInteger("teos_id"));

                StringBuilder sb = new StringBuilder();
                for (Autor a : teos.getAll(Autor.class)) {
                    if (sb.length() > 0)
                        sb.append(", ");
                    sb.append(a.toString());
                }

                teosed.add(Json.createObjectBuilder()
                        .add("id", (int) teos.getInteger("id"))
                        .add("teos", (String) teos.getString("pealkiri"))
                        .add("aasta", (int) teos.getInteger("aasta"))
                        .add("autorid", sb.toString())
                        .add("algus",laenutus.getDate("algus").toString())
                        .add("lopp", (laenutus.get("lopp")!= null ? laenutus.getDate("lopp").toString() : "")));
            }


        return Json.createObjectBuilder().add("teosed",teosed).build().toString();
    }

    public void start() {

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });
        path("/raamatukogu", () -> {
            get("/kataloog",
                    (request, response) -> {
                        String teos = request.queryParams("teos");
                        String aasta = request.queryParams("aasta");
                        String autor = request.queryParams("autor");
                        try {
                            return getKataloog(teos, aasta, autor);
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                            response.status(400);
                            return ex.getMessage();
                        }

                    });
            post("/laenuta",
                    (request, response) -> {
                        String lugeja = request.queryParams("lugeja");
                        String teos = request.queryParams("teos");
                        try {
                            return laenuta(lugeja, teos);
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                            response.status(400);
                            return ex.getMessage();
                        }
                    });
            post("/tagasta",
                    (request, response) -> {
                        String lugeja = request.queryParams("lugeja");
                        String teos = request.queryParams("teos");
                        try {
                            return tagasta(lugeja, teos);
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                            response.status(400);
                            return ex.getMessage();
                        }
                    });
            get("/laenutused",
                    (request, response) -> {
                        String lugeja = request.queryParams("lugeja");
                        try {
                            String result = getLaenutused(lugeja);
                            log.info("result => "+result);
                            return result;
                        } catch (Exception ex) {
                            log.error("error =>",ex);
                            response.status(400);
                            return ex.getMessage();
                        }
                    });
        });
    }
}
