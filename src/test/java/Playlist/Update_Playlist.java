package Playlist;

import data.BaseTestClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Update_Playlist extends BaseTestClass {

    @Test
    public void verifyStatusCodeForUpdatePlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \"updated_playlist_name\",\n" +
                        "    \"description\": \"Updated playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("playlists/"+dataClass.Playlist_Id_To_Update)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.body().print(),"");
    }

    @Test
    public void verifyStatusCodeForGetOnePlaylist() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/playlists/" + dataClass.Playlist_Id_To_Update)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
