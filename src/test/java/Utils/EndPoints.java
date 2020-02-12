package Utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;

public final class EndPoints {

    @BeforeTest
    public RequestSpecBuilder getRequestSpec () {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.openweathermap.org")
                .setBasePath("data/2.5/weather")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY);
    }

}
