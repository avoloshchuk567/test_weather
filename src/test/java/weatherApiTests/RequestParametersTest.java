package weatherApiTests;

import static io.restassured.RestAssured.*;

import Utils.EndPoints;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.*;

public class RequestParametersTest {

    private EndPoints ed = new EndPoints();
    private static final String AUTHAPPID = "APPID";
    private static final String APIKEY = "0b4a9150ecd5efe7c0d8afa2a8d1629e";
        private static int statusCode = 200;

    @DataProvider
    public Object[][] citiesAndIds() {
        return new Object[][]{
                {"q", "Kathmandu", "name", "Kathmandu", "sys.country", "NP"},
                {"q", "London", "name", "London", "sys.country", "GB"},
                {"q", "Gurzuf", "name", "Gurzuf", "sys.country", "UA"},
                {"id", "4294512", "name", "Henderson", "sys.country", "US"},
                {"id", "2166166", "name", "Frankford", "sys.country", "AU"},
                {"id", "3170334", "name", "Pompeiana", "sys.country", "IT"},
        };
    }

    @Test(dataProvider = "citiesAndIds", groups = "functional")
    public void byCityNameOrId(String requestParam, String requestValue, String responseCity,
                               String responseCityValue, String responseState, String responseStateValue) {
        given().spec(ed.getRequestSpec()
                .addParam(requestParam, requestValue)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .body(responseCity, equalTo(responseCityValue))
                .body(responseState, equalTo(responseStateValue));
    }

    @DataProvider
    public Object[][] cityState() {
        return new Object[][]{
                //{"q", "London", null, "name", "London", "sys.country", "GB"},
                {"q", "London", "uk", "name", "London", "sys.country", "GB"},
                // need to be determined
                //{"q", "Gurzuf", null, "name", "Gurzuf", "sys.country", "UA"},
                {"q", "Gurzuf", "ua", "name", "Gurzuf", "sys.country", "UA"},
                // {"q", "Kathmandu", null, "name", "Kathmandu", "sys.country", "NP"},
                {"q", "Kathmandu", "np", "name", "Kathmandu", "sys.country", "NP"},
                //{"id", "4294512", null, "name", "Henderson County", "sys.country", "US"},
                // {"id", "2166166", null, "name", "Frankford", "sys.country", "AU"},
                //{"id", "6074096", null, "name", "Millertown", "sys.country", "CA"},
        };
    }

    @Test(dataProvider = "cityState", groups = "smoke")
    public void byCityState(String requestParam, String requestValue1, String requestValue2, String responseCity,
                            String responseCityValue, String responseState, String responseStateValue) {
        given().spec(ed.getRequestSpec()
                .addParam(requestParam, requestValue1, requestValue2)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .body(responseCity, equalTo(responseCityValue))
                .body(responseState, equalTo(responseStateValue));
    }

    @DataProvider
    public Object[][] coordinates() {
        return new Object[][]{
                {"lon", "7.88836", "lat", "43.85297", "name", "Pompeiana", "sys.country", "IT"},
                {"lon", "6.84229", "lat", "46.266579", "name", "Chatel", "sys.country", "FR"},
                {"lon", "-2.72662", "lat", "40.625301", "name", "Duron", "sys.country", "ES"},

        };
    }

    @Test(enabled = false, dataProvider = "coordinates", groups = "functional")
    public void byCoordinates(String requestLon, String requestLonVal, String requestLat, String requestLatVal,
                              String responseCity, String responseCityValue, String responseState, String responseStateValue) {
        given().spec(ed.getRequestSpec()
                .addParam(requestLon, requestLonVal)
                .addParam(requestLat, requestLatVal)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .body(responseCity, equalTo(responseCityValue))
                .body(responseState, equalTo(responseStateValue));
    }
}
