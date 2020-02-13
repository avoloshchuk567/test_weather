package weatherApiTests;

import Utils.EndPoints;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ResponseParametersTest {
    EndPoints ed = new EndPoints();
    private static final String AUTHAPPID = "APPID";
    private static final String APIKEY = "0b4a9150ecd5efe7c0d8afa2a8d1629e";
    private static int statusCode = 200;

    @DataProvider
    public Object[][] responseCoordinates() {
        return new Object[][]{
                {"id", "1552494", "coord.lon", "119.38"},
                {"id", "1552494", "coord.lat", "31.02" },
                {"id", "1552494", "id", "1552494" },
                {"id", "1552494", "name", "Dongchong" },
                {"id", "1552494", "sys.country", "CN" },

        };
    }

    @Test(dataProvider = "responseCoordinates")
    public void responseCoordinatesTest(String requestId, String requestIdValue, String responseParam,
                                        String responseParamValue) {
        given().spec(ed.getRequestSpec()
                .addParam(requestId, requestIdValue)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .body(responseParam, equalTo(responseParamValue));
    }
}
