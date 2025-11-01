package Playlist;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class Create_Playlist_Without_Name extends BaseTestClass {

    @Test
    public void verifyStatusCodeForCreatePlaylist() {

        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"description\": \"Created via api\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("/users/"+dataClass.USER_ID+"/playlists")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void CheckTheExistenceOfIdAndName(){
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"description\": \"Created via api\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("/users/"+dataClass.USER_ID+"/playlists")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", notNullValue())
                .extract().response();
    }

    @Test
    public void PlayListNameMatchesTheRandomName(){
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("/users/"+dataClass.USER_ID+"/playlists")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", notNullValue())
                .extract().response();
    }
}