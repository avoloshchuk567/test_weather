package weatherApiTests;

import io.qameta.allure.*;
import utils.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("API tests for testWeather project")
@Feature("Authentication verification")

public class AuthKeyTest {
    private EndPoints endPoint1 = new EndPoints();

    private static final Logger LOG = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.testWeather.AuthKeyTest");
    private static int expectedStatusCodeUnauth = 401;
    private static int expectedStatusCodeOk = 200;
    private static String requestParamBy = "q";
    private static String requestParamByValue = "London";
    private static String requestParamAuth = "APPID";


    @Test(description = "Auth test: API call test without API Key parameter in request")
    @Description("Test Description: checked that 401 error is returned to API calls without API key")
    @Story("401 error is returned to API calls without API key")
    public void withoutAuthParam() {
        LOG.info("Authentication without API Key tests");
        given().spec(endPoint1.getBasePath()
                .addParam(requestParamBy, requestParamByValue)
                .build())
                .when()
                .get()
                .then()
                .statusCode(expectedStatusCodeUnauth);

        LOG.debug("Test: It's impossible to get data without APPID");
    }

    @DataProvider
    public Object[][] requestData() {
        return new Object[][]{
                {null, expectedStatusCodeUnauth, "verifyNullAppId"},
                {"4t5gdg534df453", expectedStatusCodeUnauth, "verifyIncorrectAppIdShort"},
                {"0b4a9150dfd5e34fc0d8afa2a8d1629f", expectedStatusCodeUnauth, "verifyIncorrectAppIdFull"},
                {"0b4a9150ecd5efe7c0d8afa2a8d1629e", expectedStatusCodeOk, "verifyValidAppId"}

        };
    }

    @Test(dataProvider = "requestData", dependsOnMethods = {"withoutAuthParam"}, description = "Auth test: API call tests with invalid and valid API Keys in request")
    @Description("Test description: check that {expectedStatusCode} status code is returned for API Key = {appId}")
    @Story("200 status code is returned only if API Key is correct")
    public void testAuthKey(String appId, int expectedStatusCode, String testDataDescription) {
        LOG.info("Authentication with incorrect and valid API Keys");
        Response wholeResponse = given().spec(endPoint1.getBasePath()
                .addParam(requestParamBy, requestParamByValue)
                .addParam(requestParamAuth, appId)
                .build())
                .when()
                .get();
        LOG.debug("Test: {}, expected value: {} = {} ", testDataDescription, "expectedStatus code", expectedStatusCode);
        LOG.debug("Request address: {} \n", wholeResponse.getHeaders().getValue("X-Cache-Key"));
        assertThat(wholeResponse.getStatusCode(), equalTo(expectedStatusCode));
    }
}