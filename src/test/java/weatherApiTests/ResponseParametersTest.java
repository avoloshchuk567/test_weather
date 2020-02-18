package weatherApiTests;

import Utils.EndPoints;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseParametersTest {
    static final Logger LOG = LoggerFactory.getLogger(ResponseParametersTest.class);
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

    @Test(dataProvider = "responseParameterToCheck", description = "Response test: Test of parameters returned in response")
    @Description("Test description: check required parameters in response")
    public void responseCoordinatesTest(String requestId, String requestIdValue,
                                        String responseParam, String responseParamExpectedValue,
                                        String testDataDescription) {
        LOG.info("Verification of the parameters in response");
        Response wholeResponse = (given().spec(endPoint1.getBasePath()
                .addParam(requestId, requestIdValue)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get());
        LOG.debug("Test: {}, expected parameter: {} = {} ", testDataDescription, responseParam, responseParamExpectedValue);
        LOG.debug("Request address: {}", wholeResponse.getHeaders().getValue("X-Cache-Key"));
        LOG.debug("Response body: {}", wholeResponse.getBody().asString());


        String responseParamActualValue = wholeResponse.getBody()
                .jsonPath()
                .get(responseParam)
                .toString();
        assertThat(responseParamActualValue, equalTo(responseParamExpectedValue));
    }
}