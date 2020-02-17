package weatherApiTests;

import Utils.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthKeyTest {
    private EndPoints endPoint1 = new EndPoints();

    private static final Logger LOG = LoggerFactory.getLogger(AuthKeyTest.class);
    private static int expectedStatusCodeUnath = 401;
    private static int expectedStatusCodeOk = 200;
    private static String requestParamBy = "q";
    private static String requestParamByValue = "London";
    private static String requestParamAuth = "APPID";


    @Test(groups = {"functional"})
    public void withoutAuthParam() {
        LOG.debug("Authentication without API Key tests");
        given().spec(endPoint1.getBasePath()
                .addParam(requestParamBy, requestParamByValue)
                .build())
                .when()
                .get()
                .then()
                .statusCode(expectedStatusCodeUnath);

        LOG.debug("Test: It's impossible to get data without APPID");
    }

    @DataProvider
    public Object[][] requestData() {
        return new Object[][]{
                {null, expectedStatusCodeUnath, "verifyNullAppId"},
                {"4t5gdg534df453", expectedStatusCodeUnath, "verifyIncorrectAppIdShorl"},
                {"0b4a9150dfd5e34fc0d8afa2a8d1629f", expectedStatusCodeUnath, "verifyIncorrectAppIdFull"},
                {"0b4a9150ecd5efe7c0d8afa2a8d1629e", expectedStatusCodeOk, "verifyValidAppId"}

        };
    }

    @Test(dataProvider = "requestData", groups = {"smoke", "functional"}, dependsOnMethods = {"withoutAuthParam"})
    public void testAuthKey(String appId, int expectedStatusCode, String testDataDescription) {
        LOG.debug("Authentication with incorrect and valid API Keys");
        Response wholeResponse = given().spec(endPoint1.getBasePath()
                .addParam(requestParamBy, requestParamByValue)
                .addParam(requestParamAuth, appId)
                .build())
                .when()
                .get();
        LOG.debug("Test: {}, expected value: {} = {} ", testDataDescription, "expectedStatus code", expectedStatusCode);
        LOG.debug("Request address: {}", wholeResponse.getHeaders().getValue("X-Cache-Key"));
        assertThat(wholeResponse.getStatusCode(), equalTo(expectedStatusCode));
    }
}