import models.Autor;
import models.Teos;
import org.javalite.activejdbc.Base;

import java.util.List;

/**
 * Created by raydnoper on 18/02/17.
 */
public class Raamatukogu {


    static void startDB() {
            Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost/raamatukogu", "raamatukogu", "raamatukogu");
    }

    static void test() {
        List<Teos> teosed = Teos.findAll();

        for (Teos t: teosed) {
            System.out.print(t.toString() );
            for (Autor a : t.getAll(Autor.class)) {
                System.out.print(a.toString()) ;
            }
            System.out.println();
        }

        System.out.println("Testime raamatukogu");
    }


    public static void main(String[] args) {

        startDB();

        RaamatukoguWeb web = new RaamatukoguWeb();
        web.start();

        RaamatukoguService service = new RaamatukoguService();
        service.start();




       // test();

    }
}
