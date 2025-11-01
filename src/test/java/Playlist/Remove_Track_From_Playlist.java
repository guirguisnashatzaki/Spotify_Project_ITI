package Playlist;

import data.BaseTestClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AnyOf.anyOf;

public class Remove_Track_From_Playlist extends BaseTestClass {

    @Test
    public void verifyStatusCodeForRemoveTrackFromPlaylist() {

        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body("{\n" +
                        "    \"tracks\": [\n" +
                        "        {\n" +
                        "            \"uri\":\"spotify:track:"+dataClass.TRACK_ID+"\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"snapshot_id\": \"{{snapshot_id}}\"\n" +
                        "}")
                .when()
                .delete("playlists/"+dataClass.Playlist_Id_To_Update+"/tracks")
                .then()
                .assertThat()
                .statusCode(200)
                .body("snapshot_id", anyOf(org.hamcrest.Matchers.notNullValue()), org.hamcrest.Matchers.nullValue())
                .extract().response();
        System.out.println(response.body().prettyPrint());
    }

    @Test
    public void verifyIdempotentForRemoveTrackFromPlaylist() {

        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \""+dataClass.TRACK_URI+"\"\n" +
                        "    ],\n" +
                        "    \"position\": 0\n" +
                        "}")
                .when()
                .delete("playlists/"+dataClass.Playlist_Id_To_Update+"/tracks")
                .then()
                .assertThat()
                .statusCode(anyOf(is(200), is(204), anyOf(is(400), is(403))))
                .body("snapshot_id", anyOf(org.hamcrest.Matchers.notNullValue(), org.hamcrest.Matchers.nullValue()))
                .extract().response();
        String snapshotId = response.jsonPath().getString("snapshot_id");
    }

    @Test
    public void verifyStatusCodeForGetOnePlaylist() {
        Response response = given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .accept( "application/json")
                .get("/playlists/" + dataClass.Playlist_Id_To_Update)
                .then()
                .assertThat()
                .statusCode(200).extract().response();

        System.out.println(response.body().prettyPrint());
        Response response1 = given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .accept("application/json")
                .get("/playlists/" + dataClass.PLAYLIST_ID)
                .then()
                .extract()
                .response();

        String rawBody = response.asString();
        JsonPath jsonPath = new JsonPath(rawBody);
        String trackid = jsonPath.getString("tracks.items[0].track.id");
        Assert.assertTrue(trackid.contains(dataClass.TRACK_ID), "Track not found in the playlist");
    }

}
