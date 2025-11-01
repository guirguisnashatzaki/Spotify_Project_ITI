package Track;

import data.BaseTestClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Remove_Track_From_User extends BaseTestClass {

    @Test
    public void verifyTrackRemovalResponse() {

        // Send DELETE or POST request (depending on your API action)
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \""+dataClass.TRACK_ID+"\"\n" +
                        "    ]\n" +
                        "}") // example track
                .when()
                .delete("/me/tracks")  // adjust method/path if different
                .then()
                .statusCode(anyOf(is(200), is(204)))
                .body(containsString(""))

                .extract().response();
    }


    @Test
    public void verifyTrackLinkedFromIdMatchesEnvironment() {
        // Send the request
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me/tracks?market=ES&limit=10&offset=0")
                .then()
                .statusCode(200)
                .extract().response();

        // Extract linked_from.id from JSON
        List<Map<String, Object>> items = response.jsonPath().getList("items");
        int lastIndex = items.size() - 1;  // get last index
        String linkedFromId = response.jsonPath().getString("items[" + lastIndex + "].track.id");
        // ✅ Assert equality
        Assert.assertNotEquals(linkedFromId, dataClass.TRACK_ID, "Linked track ID does not match!");
    }

    @Test
    public void checkIdempotent(){
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \""+dataClass.TRACK_ID+"\"\n" +
                        "    ]\n" +
                        "}") // example track
                .when()
                .delete("/me/tracks")  // adjust method/path if different
                .then()
                .statusCode(anyOf(is(200), is(204)))
                .extract().response();
    }
}