import static spark.Spark.get;
import static spark.Spark.staticFiles;

/**
 * Created by raydnoper on 20/02/17.
 */
public class RaamatukoguWeb {

    public void start() {
        get("/", (req, res) -> "test rmtk");

    }
}
