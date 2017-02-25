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

    private void testConnection() {
        if (!Base.hasConnection()) {
            Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost/raamatukogu", "raamatukogu", "raamatukogu");
        }
    }

    public RaamatukoguService() {
        log.info("RaamatukoguService startis...");
        testConnection();

    }

    public String getKataloog(String fTeos, String fAasta, String fAutor) {
        log.info("teos:" + fTeos);
        int nAasta = 0;
        if (fAasta != null && !fAasta.isEmpty())
            nAasta = Integer.parseInt(fAasta);
        log.info("aasta:" + nAasta);

        testConnection();

        JsonObjectBuilder kataloog = Json.createObjectBuilder();
        kataloog.add("teosFilter", fTeos == null ? "" : fTeos);
        kataloog.add("aastaFilter", fAasta == null ? "" : fAasta);
        JsonArrayBuilder teosed = Json.createArrayBuilder();
        List<Teos> list = null;

        List<Object> params = new ArrayList();
        String query = "";

        // Ehitame dynaamiliselt p2ringu
        if (nAasta != 0) {
            log.info("aasta=" + nAasta);
            query += (query.isEmpty() ? "" : " AND ") + " teosed.aasta=?";
            params.add(nAasta);
        }
        if (fTeos != null) {
            log.info("pealkiri=" + fTeos);
            query += (query.isEmpty() ? "" : " AND ") + " teosed.pealkiri ILIKE ?";
            params.add("%" + fTeos + "%");
        }
        if (fAutor != null) {
            log.info("autor=" + fAutor);
            query += (query.isEmpty() ? "" : " AND ") + " autorid.nimi ILIKE ?";
            params.add("%" + fAutor + "%");
        }

        log.info(query);
        log.info(Arrays.toString(params.toArray()));

        list = Teos.findBySQL("SELECT teosed.*, autorid.nimi FROM teosed " +
                " LEFT JOIN autor_teos ON teosed.id=autor_teos.teos_id " +
                " LEFT JOIN autorid ON autor_teos.autor_id=autorid.id " +
                " WHERE " + query, params.toArray());

        if (list != null)
            for (Teos teos : list) {
                StringBuilder sb = new StringBuilder();
                for (Autor a : teos.getAll(Autor.class)) {
                    if (sb.length() > 0)
                        sb.append(", ");
                    sb.append(a.getString("nimi"));
                    if (a.getString("synniaeg") != null || a.getString("surmaaeg") != null) {
                        sb.append(" (")
                                .append(a.getString("synniaeg"))
                                .append("-")
                                .append(a.getString("surmaaeg"))
                                .append(")");
                    }

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

        testConnection();

        Lugeja lugeja = Lugeja.first("nimi=?", lugejaNimi);

        if (lugeja == null)
            throw new Exception("Sellist lugejat ei eksisteeri");

        System.out.println(lugeja.toString());

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

        return "";
    }

    String tagasta(String lugeja, String teos) {
        return "";
    }

    public void start() {

        get("/test", (req, res) -> "test rmtk");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            log.info("request:" + request.toString());
            log.info("response:" + response.toString());
        });
        path("/raamatukogu", () -> {

            get("/kataloog",
                    (request, response) -> {
                        System.out.println(request.queryParams());
                        String teos = request.queryParams("teos");
                        String aasta = request.queryParams("aasta");
                        String autor = request.queryParams("autor");
                        return getKataloog(teos, aasta, autor);
                    });
            post("/laenutus",
                    (request, response) -> {
                        String lugeja = request.queryParams("lugeja");
                        String teos = request.queryParams("teos");
                        try {
                            return laenuta(lugeja, teos);
                        } catch (Exception ex) {
                            response.status(400);

                            return ex.getMessage();
                        }
                    });
            post("/tagastus/",
                    (request, response) -> {
                        String lugeja = request.queryParams("lugeja");
                        String teos = request.queryParams("teos");
                        try {
                            return tagasta(lugeja, teos);
                        } catch (Exception ex) {
                            response.status(400);

                            return ex.getMessage();
                        }
                    });
            get("/laenutused/:lugeja",
                    (request, response) -> {
                        int lugeja = Integer.parseInt(request.params("lugeja"));
                        return "";
                    });
        });
    }
}
