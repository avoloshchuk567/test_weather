package Utils;

import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.asserts.*;

public class HelperMethods {
    public static void checkStatusIs200 (Response res) {
        Assert.assertEquals(res.getStatusCode(), 200, "Status check");


    }
}
