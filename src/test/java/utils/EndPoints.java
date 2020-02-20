package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

public final class EndPoints {

    @BeforeTest
    public RequestSpecBuilder getBasePath() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.openweathermap.org")
                .setBasePath("data/2.5/weather")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY);
    }

}
