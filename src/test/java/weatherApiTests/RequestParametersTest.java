package weatherApiTests;

import static io.restassured.RestAssured.*;

import Utils.EndPoints;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.*;

public class RequestParametersTest {

    private EndPoints ed = new EndPoints();
    private static final String AUTHAPPID = "APPID";
    private static final String APIKEY = "0b4a9150ecd5efe7c0d8afa2a8d1629e";

    @Test
    public void byCityName() {
        given().spec(ed.getRequestSpec()
                .addParam("q", "London")
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", equalTo("London"))
                .body("sys.country", equalTo("GB"));
    }

    @Test
    public void byCityState() {
        given().spec(ed.getRequestSpec()
                .addParam("q", "London", "uk")
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", equalTo("London"))
                .body("sys.country", equalTo("GB"));
    }

    @Test
    public void byId() {
        given().spec(ed.getRequestSpec()
                .addParam("id", "2643743")
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", equalTo("London"))
                .body("sys.country", equalTo("GB"));
    }

    @Test
    public void byCoordinates() {
        given().spec(ed.getRequestSpec()
                .addParam("lon", "34.283333")
                .addParam("lat", "44.549999")
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", equalTo("Gurzuf"))
                .body("sys.country", equalTo("UA"));
    }
}
