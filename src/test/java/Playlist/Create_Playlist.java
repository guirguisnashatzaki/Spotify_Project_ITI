package Playlist;

import data.BaseTestClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class Create_Playlist extends BaseTestClass {

    @Test
    public void verifyStatusCodeForCreatePlaylist() {
        String randomPlaylistName = "Playlist " + ((int)(Math.random() * 100) + 1);

        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \""+randomPlaylistName+"\",\n" +
                        "    \"description\": \"Created via api\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("/users/"+dataClass.USER_ID+"/playlists")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().response();
    }

    @Test
    public void CheckTheExistenceOfIdAndName(){
        String randomPlaylistName = "Playlist " + ((int)(Math.random() * 100) + 1);
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \""+randomPlaylistName+"\",\n" +
                        "    \"description\": \"Created via api\",\n" +
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
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \""+randomPlaylistName+"\",\n" +
                        "    \"description\": \"Created via api\",\n" +
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

        String playlistId = response.jsonPath().getString("id");
        dataClass.PLAYLIST_ID = playlistId;
    }

    @Test
    public void verifyStatusCodeForGetOnePlaylistWithWrongId() {
        Response response = given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/playlists/AAAAAAAAAAAAAAAAAAAAAAAAA")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().response();

        System.out.println(response.body().prettyPrint());
    }

    @Test
    public void verifyStatusCodeForGetOnePlaylist() {
        Response response = given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/playlists/" + dataClass.Playlist_Id_To_Update)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        System.out.println(response.body().prettyPrint());
    }


}
