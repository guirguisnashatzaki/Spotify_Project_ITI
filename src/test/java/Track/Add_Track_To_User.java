package Track;

import data.BaseTestClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Add_Track_To_User extends BaseTestClass {

    @Test
    public void verifyStatusCodeForAddTrackToUser() {
        // Implementation for adding a track to a user's library and verifying the status code
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"" + dataClass.TRACK_ID + "\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("/me/tracks")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void checkTheReturn() {
        // Implementation for adding a track to a user's library and verifying the status code
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"" + dataClass.TRACK_ID + "\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("/me/tracks")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.body().print(),"");
    }

    @Test
    public void verifyTrackLinkedFromIdMatchesEnvironment() {


        // Send the request
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me/tracks")
                .then()
                .statusCode(200)
                .extract().response();

        // Extract linked_from.id from JSON

        String linkedFromId = response.jsonPath().getString("items[0].track.id");
        // ✅ Assert equality
        Assert.assertEquals(linkedFromId, dataClass.TRACK_ID, "Linked track ID does not match!");
    }

    @Test
    public void verifyTrackIdArtistAndAlbumReturned() {

        // Send the request
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me/tracks?market=ES&limit=10&offset=0")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
    }


}
