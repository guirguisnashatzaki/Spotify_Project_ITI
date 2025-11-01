package Playlist;

import data.BaseTestClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Add_Track_To_Playlist extends BaseTestClass {

    @Test
    public void verifyStatusCodeForAddTrackToPlaylist() {
        // Implementation for adding track to playlist and verifying status code
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:"+dataClass.TRACK_ID+"\"\n" +
                        "    ],\n" +
                        "    \"position\": 0\n" +
                        "}")
                .when()
                .post("playlists/"+dataClass.Playlist_Id_To_Update+"/tracks")
                .then()
                .assertThat()
                .statusCode(201)
                .body("snapshot_id", org.hamcrest.Matchers.notNullValue())
                .extract().response();
        System.out.println(response.body().asString());
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
        Response tracks = given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/playlists/" + dataClass.Playlist_Id_To_Update)
                .then()
                .extract().response();

        List<Map<String, Object>> items = tracks.jsonPath().getList("tracks.items");

            String id_to_be_tested = tracks.jsonPath().getString("tracks.items[0].track.id");
        Assert.assertTrue(id_to_be_tested.contains(dataClass.TRACK_ID), "Track found in the playlist");
    }

}
