package Playlist;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class Create_Playlist_Without_Description extends BaseTestClass {

    @Test
    public void verifyStatusCodeForCreatePlaylist() {
        String randomPlaylistName = "Playlist " + ((int)(Math.random() * 100) + 1);

        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \""+randomPlaylistName+"\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("/users/"+dataClass.USER_ID+"/playlists")
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", notNullValue())
                .extract().response();
    }


    @Test
    public void PlayListNameMatchesTheRandomName(){
        String randomPlaylistName = "Playlist " + ((int)(Math.random() * 100) + 1);
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \""+randomPlaylistName+"\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("/users/"+dataClass.USER_ID+"/playlists")
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", notNullValue())
                .assertThat().body("name", org.hamcrest.Matchers.equalTo(randomPlaylistName))
                .extract().response();
    }


}
