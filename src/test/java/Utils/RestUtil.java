package Utils;

//import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class RestUtil {
    public static String path;

    public static void setBaseURI (String baseURI){ baseURI = baseURI; }
    public static void setBasePath (String basePathTerm) { basePath = basePathTerm; }
    public static void resetBaseURI(){ baseURI = null; }
    public static void resetBasePath(){ basePath = null; }
    public static void setContentType(ContentType Type){ given().contentType(Type); }
    public static void createQueryPath(String city) {
        path = city;
    }
    public static Response getResponse() {return get(path);}
    public static JsonPath getJsonPath(Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }


}
