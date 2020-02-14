package weatherApiTests;

import Utils.EndPoints;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseParametersTest {
    EndPoints endPoint1 = new EndPoints();
    private static final String AUTHAPPID = "APPID";
    private static final String APIKEY = "0b4a9150ecd5efe7c0d8afa2a8d1629e";

    @DataProvider
    public Object[][] responseParameterToCheck() {
        return new Object[][]{
                {"id", "1552494", "coord.lon", "119.38", "verifyLongitudeIsCorrect"},
                {"id", "1552494", "coord.lat", "31.02", "verifyLatitudeIsCorrect"},
                {"id", "1552494", "id", "1552494", "verifyIdIsCorrect"},
                {"id", "1552494", "name", "Dongchong", "VerifyCityIsCorrect"},
                {"id", "1552494", "sys.country", "CN", "verifyCountryIsCorrect"},

        };
    }

    @Test(dataProvider = "responseParameterToCheck")
    public void responseCoordinatesTest(String requestId, String requestIdValue,
                                        String responseParam, String responseParamExpectedValue,
                                        String testDataDescription) {

        String responseParamActualValue = (given().spec(endPoint1.getBasePath()
                .addParam(requestId, requestIdValue)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get())
                .getBody()
                .jsonPath()
                .get(responseParam)
                .toString();
        assertThat(responseParamActualValue, equalTo(responseParamExpectedValue));
    }
}