package Playlist;

import data.BaseTestClass;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class Create_Playlist_With_Different_Scope extends BaseTestClass {


    @Test
    public void createPlaylistWithWrongScope() {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN_WITH_DIFFERENT_SCOPE)
                .body("{\n" +
                        "  \"name\": \"Test Playlist\",\n" +
                        "  \"description\": \"Testing scope failure\",\n" +
                        "  \"public\": false\n" +
                        "}")
                .when()
                .post("/users/{user_id}/playlists", "your_user_id_here")
                .then()
                .statusCode(403)   // Spotify usually returns 403 for insufficient scope
                .body("error.status", equalTo(403))
                .body("error.message", containsString("insufficient"))
                .log().all();
    }

}
