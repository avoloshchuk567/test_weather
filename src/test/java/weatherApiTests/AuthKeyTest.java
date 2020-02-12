package weatherApiTests;

import Utils.EndPoints;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo; // todo: not needed import

public class AuthKeyTest {
    private EndPoints ed = new EndPoints(); // todo: not descriptive naming

    @DataProvider
    public Object[][] requestData() {
//todo: how about to add some description to test data?
//todo: and how do you know which of these APPID is valid or not? %)
        return new Object[][]{
                {null, 401},
                {"4t5gdg534df453", 401},
                {"0b4a9150dfd5e34fc0d8afa2a8d1629f", 401},
                {"0b4a9150ecd5efe7c0d8afa2a8d1629e", 200}

        };
    }

    @Test
    public void withoutAuthParam(){
        given().spec(ed.getRequestSpec()
                .addParam("q", "London")
                .build())
                .when()
                .get()
                .then()
                .statusCode(401);
    }

    @Test(dataProvider = "requestData")
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
}