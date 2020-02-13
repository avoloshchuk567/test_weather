package weatherApiTests;

import Utils.EndPoints;
import com.beust.jcommander.Parameter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthKeyTest {
    private EndPoints ed = new EndPoints();

    @DataProvider
    @Parameters({"statusCodeUnath", "statusCodeOk"})
    public Object[][] requestData(int statusCodeUnath, int statusCodeOk) {
        return new Object[][]{
                {null, statusCodeUnath},
                {"4t5gdg534df453", statusCodeUnath},
                {"0b4a9150dfd5e34fc0d8afa2a8d1629f", statusCodeUnath},
                {"0b4a9150ecd5efe7c0d8afa2a8d1629e", statusCodeOk}

        };
    }

    @Test(dataProvider = "requestData", groups = {"smoke", "functional"}, dependsOnMethods = {"withoutAuthParam"})
    public void testAuthKey(String appId, int statusCode) {
        given().spec(ed.getRequestSpec()
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
    public void withoutAuthParam(int statusCodeUnath){
        given().spec(ed.getRequestSpec()
                .addParam("q", "London")
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCodeUnath);
    }
}