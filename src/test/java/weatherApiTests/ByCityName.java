package weatherApiTests;

import static io.restassured.RestAssured.*;
import Utils.EndPoints;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.*;

public class ByCityName {

    EndPoints ed = new EndPoints();

    @Test
    public void t01() {
        given().spec(ed.getRequestSpec()
                    .addParam("q", "London")
                    .addParam("APPID","0b4a9150ecd5efe7c0d8afa2a8d1629e")
                    .build())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", equalTo("London"));
    }


}
