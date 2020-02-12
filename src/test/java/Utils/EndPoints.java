package Utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;

public final class EndPoints {

    @BeforeTest
    public RequestSpecBuilder getRequestSpec () { // todo: it's not ReqSpec - , but it's builder please change the name
         return new RequestSpecBuilder()
                .setBaseUri("https://api.openweathermap.org")
                .setBasePath("data/2.5/weather")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY);
    }

}
