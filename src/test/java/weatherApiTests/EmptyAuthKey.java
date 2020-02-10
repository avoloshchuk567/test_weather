package weatherApiTests;

import Utils.EndPoints;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EmptyAuthKey {
    private EndPoints ed = new EndPoints();

    @Test
    public void t01() {
        given().spec(ed.getRequestSpec()
                .addParam("q", "London")
                .addParam("APPID","")
                .build())
                .when()
                .get()
                .then()
                .statusCode(401);
    }
}
