package weatherApiTests;

import Utils.*;
import io.restassured.http.ContentType;
import org.testng.annotations.*;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

public class ByCityName {

    private Response res = null; //Response
    private JsonPath jp = null;

    @BeforeTest
    public void setup(){
        RestUtil.setBaseURI("https://api.openweathermap.org");
        RestUtil.setBasePath("data/2.5/weather?=");
        RestUtil.path = "London";
        RestUtil.setContentType(ContentType.JSON);
    }

    @Test
    public void T01_StatusCodeTest() {
        HelperMethods.checkStatusIs200(res);
    }

    @AfterTest
    public void afterTest (){
        RestUtil.resetBaseURI();
        RestUtil.resetBasePath();
    }

}
