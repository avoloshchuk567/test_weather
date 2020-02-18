package weatherApiTests;

import Utils.EndPoints;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestParametersTest {

    static final Logger LOG = LoggerFactory.getLogger(RequestParametersTest.class);

    private EndPoints endPoint1 = new EndPoints();
    private static final String AUTHAPPID = "APPID";
    private static final String APIKEY = "0b4a9150ecd5efe7c0d8afa2a8d1629e";
    private static int expectedStatusCode = 200;

    @DataProvider
    public Object[][] cityStateID() {
        return new Object[][]{
                {"q", "London", "name", "London", "sys.country", "GB", "byCityRequest"},
                {"q", "London,uk", "name", "London", "sys.country", "GB", "ByCityStateRequest"},
                {"q", "Gurzuf", "name", "Gurzuf", "sys.country", "UA", "byCityRequest"},
                {"q", "Gurzuf,ua", "name", "Gurzuf", "sys.country", "UA", "ByCityStateRequest"},
                {"q", "Kathmandu", "name", "Kathmandu", "sys.country", "NP", "ByCityRequest"},
                {"q", "Kathmandu,np", "name", "Kathmandu", "sys.country", "NP", "ByCityStateRequest"},
                {"id", "4294512", "name", "Henderson", "sys.country", "US", "byIdRequest"},
                {"id", "2166166", "name", "Frankford", "sys.country", "AU", "byIdRequest"},
                {"id", "6074096", "name", "Millertown", "sys.country", "CA", "byIdRequest"},
        };
    }


    @Test(dataProvider = "cityStateID", groups = "smoke", description = "Request test: Request is based on city, city&stats, id")
    @Description("Test Description: send request based on city, city&stats, id")
    public void byCityOrCityStateOrId(String requestParam, String requestParamValue, String responseCity,
                                      String responseCityValue, String responseState, String responseStateValue,
                                      String testDataDescription) {
        LOG.info("Test of request parameters by city, city/state, id");
        Response wholeResponse = given().spec(endPoint1.getBasePath()
                .addParam(requestParam, requestParamValue)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get();
        LOG.debug("Test: {}, request parameters: {} = {} ", testDataDescription, requestParam, requestParamValue);
        LOG.debug("Request address: {}", wholeResponse.getHeaders().getValue("X-Cache-Key"));
        LOG.debug("Response body: {} \n", wholeResponse.getBody().asString());

        assertThat(wholeResponse.getStatusCode(), equalTo(expectedStatusCode));
        assertThat(wholeResponse.getBody().jsonPath().get(responseCity).toString(), equalTo(responseCityValue));
        assertThat(wholeResponse.getBody().jsonPath().get(responseState).toString(), equalTo(responseStateValue));
    }

    @DataProvider
    public Object[][] coordinates() {
        return new Object[][]{
                {"lon", "7.88836", "lat", "43.85297", "name", "Pompeiana", "sys.country", "IT", "byCoordinatesRequest"},
                {"lon", "6.84229", "lat", "46.266579", "name", "Chatel", "sys.country", "FR", "byCoordinatesRequest"},
                {"lon", "-2.72662", "lat", "40.625301", "name", "Duron", "sys.country", "ES", "byCoordinatesRequest"},

        };
    }

    @Test(dataProvider = "coordinates", groups = "functional", description = "Request test: Request is based on coordinates")
    @Description("Test description: send request based on coordinates")
    public void byCoordinates(String requestLon, String requestLonVal, String requestLat, String requestLatVal,
                              String responseCity, String responseCityValue, String responseState, String responseStateValue,
                              String testDataDescription) {
        LOG.info("Test of request parameters by coordinates");
        Response wholeResponse = given().spec(endPoint1.getBasePath()
                .addParam(requestLon, requestLonVal)
                .addParam(requestLat, requestLatVal)
                .addParam(AUTHAPPID, APIKEY)
                .build())
                .when()
                .get();

        LOG.debug("Test: {}, request parameters coordinates: {} = {} , {} = {}", testDataDescription, requestLon, requestLonVal, requestLat, requestLatVal);
        LOG.debug("Request address: {}", wholeResponse.getHeaders().getValue("X-Cache-Key"));
        LOG.debug("Response body: {} \n", wholeResponse.getBody().asString());
        assertThat(wholeResponse.getStatusCode(), equalTo(expectedStatusCode));
        assertThat(wholeResponse.getBody().jsonPath().get(responseCity).toString(), equalTo(responseCityValue));
        assertThat(wholeResponse.getBody().jsonPath().get(responseState).toString(), equalTo(responseStateValue));
    }
}