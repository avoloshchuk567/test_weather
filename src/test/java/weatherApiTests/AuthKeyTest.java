package weatherApiTests;

import Utils.EndPoints;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthKeyTest {
    private EndPoints endPoint1 = new EndPoints();

    @DataProvider
    @Parameters({"statusCodeUnath", "statusCodeOk"})
    public Object[][] requestData(int statusCodeUnath, int statusCodeOk) {
        return new Object[][]{
                {null, statusCodeUnath, "verifyNullAppId"},
                {"4t5gdg534df453", statusCodeUnath, "verifyIncorrectAppIdShorl"},
                {"0b4a9150dfd5e34fc0d8afa2a8d1629f", statusCodeUnath,"verifyIncorrectAppIdFull"},
                {"0b4a9150ecd5efe7c0d8afa2a8d1629e", statusCodeOk, "verifyValidAppId"}

        };
    }

    @Test(dataProvider = "requestData", groups = {"smoke", "functional"}, dependsOnMethods = {"withoutAuthParam"})
    public void testAuthKey(String appId, int statusCode, String testDataDescription) {
        given().spec(endPoint1.getBasePath()
                .addParam("q", "London")
                .addParam("APPID", appId)
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCode);
    }

    @Test(groups = {"functional"})
    @Parameters("statusCodeUnath")
    public void withoutAuthParam(int statusCodeUnath) {
        given().spec(endPoint1.getBasePath()
                .addParam("q", "London")
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCodeUnath);
    }
}