package Utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public final class EndPoints {

    public RequestSpecBuilder getRequestSpec () {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.openweathermap.org")
                .setBasePath("data/2.5/weather")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY);
    }

}
