import models.Autor;
import models.Teos;
import org.javalite.activejdbc.Base;
import spark.servlet.SparkApplication;

import java.util.List;

/**
 * Created by raydnoper on 18/02/17.
 */
public class Raamatukogu implements SparkApplication{


    static void startDB() {
            Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost/raamatukogu", "raamatukogu", "raamatukogu");
    }

    /**
     * Standalone serveri käivitusmeetod
     * @param args
     */
    public static void main(String[] args) {

        startDB();

        RaamatukoguService service = new RaamatukoguService();
        service.start();

    }

    public void init() {
        startDB();
        RaamatukoguService service = new RaamatukoguService();
        service.start();
    }


}
