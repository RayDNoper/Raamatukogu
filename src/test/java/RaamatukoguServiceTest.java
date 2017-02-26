import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParserFactory;
import java.io.StringReader;

/**
 * Created by raydnoper on 26/02/17.
 */
public class RaamatukoguServiceTest {

    RaamatukoguService service = new RaamatukoguService();

    @BeforeClass
    public void startUp() {
        service.start();
    }

    /**
     * 1. Kontrolli, et teenuse baasiühendus on avatud
     */
    @Test
    public void testServiceStartup() {
        Assert.assertTrue(service.testConnection());

    }

    /**
     * 1. Leia olematu lugeja laenutused;
     * 2. kontrolli, et olematu lugeja laenutuste list on tühi.
     * @throws Exception
     */
    @Test
    public void testOlematuLugeja() throws Exception{
        String olematuLugejaLaenutused = service.getLaenutused("Olematu Lugeja");

        Assert.assertEquals(olematuLugejaLaenutused, "{\"teosed\":[]}" );
    }

    /**
     * 1. Laenuta lugejale "Juhan Tester" teos ID-ga 45 (The Illustrated Man, Ray Bradbury);
     * 2. kontrolli, et antud lugejal on laenutusi;
     * 3. tagasta antud raamat.
     * @throws Exception
     */
    @Test
    public void testEksisteerivLugejaLaenutusTagastus() throws Exception {
        service.laenuta("Juhan Tester", "45");

        String eksisteerivaLugejaLaenutused = service.getLaenutused("Juhan Tester");

        Assert.assertNotEquals(eksisteerivaLugejaLaenutused, "{\"teosed\":[]}" );;

        service.tagasta("Juhan Tester", "45");

    }

    /**
     * 1. Leia kataloogist Clifford D. Simaki raamat City (1981);
     * 2. kontrolli, et filtrid klapivad;
     * 3. kontrolli, et leitakse teos ja selle ID on 33.
     * @throws Exception
     */
    @Test
    public void testKataloogFilter() throws Exception {
        String city = service.getKataloog("City", "1981", "Simak");

        JsonObject obj = Json.createReader(new StringReader(city)).readObject();

        Assert.assertEquals(obj.getJsonArray("filtrid").toString(), "[{\"teos\":\"City\"},{\"aasta\":\"1981\"},{\"autor\":\"Simak\"}]" );

        Assert.assertEquals(obj.getJsonArray("teosed").getJsonObject(0).getInt("id"), 33);
    }
}
