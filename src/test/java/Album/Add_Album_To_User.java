package Album;

import data.BaseTestClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Add_Album_To_User extends BaseTestClass {

    @Test
    public void verifyStatusCodeForAddAlbumToUser() {
        // Implementation for adding an album to a user's library and verifying the status code
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"" + dataClass.ALBUM_ID + "\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("/me/albums")
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
                        "        \"" + dataClass.ALBUM_ID + "\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("/me/albums")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.body().print(),"");
    }

    @Test
    public void verifyAlbumLinkedFromIdMatchesEnvironment() {


        // Send the request
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me/albums?market=ES&limit=10&offset=0")
                .then()
                .statusCode(200)
                .extract().response();

        // Extract id from JSON
        List<Map<String, Object>> items = response.jsonPath().getList("items");
        int lastIndex = 0;  // get last index
        String linkedFromId = response.jsonPath().getString("items[" + lastIndex + "].album.id");

        // ✅ Assert equality
        Assert.assertEquals(linkedFromId, dataClass.ALBUM_ID, "Linked album ID does not match!");
    }

    @Test
    public void verifyAlbumHasRequiredProperties() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.spotify.com/v1/me/albums")
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();

        int itemCount = jsonPath.getList("items").size();

        Map<String, Object> lastAlbum = jsonPath.getMap("items[" + (itemCount - 1) + "].album");

        Assert.assertNotNull(lastAlbum.get("album_type"), "album_type missing");
        Assert.assertNotNull(lastAlbum.get("total_tracks"), "total_tracks missing");
        Assert.assertNotNull(lastAlbum.get("external_urls"), "external_urls missing");
        Assert.assertNotNull(lastAlbum.get("href"), "href missing");
        Assert.assertNotNull(lastAlbum.get("id"), "id missing");
        Assert.assertNotNull(lastAlbum.get("images"), "images missing");
        Assert.assertNotNull(lastAlbum.get("name"), "name missing");
        Assert.assertNotNull(lastAlbum.get("release_date"), "release_date missing");
        Assert.assertNotNull(lastAlbum.get("release_date_precision"), "release_date_precision missing");
        Assert.assertNotNull(lastAlbum.get("type"), "type missing");
        Assert.assertNotNull(lastAlbum.get("uri"), "uri missing");
        Assert.assertNotNull(lastAlbum.get("artists"), "artists missing");
        Assert.assertNotNull(lastAlbum.get("tracks"), "tracks missing");
        Assert.assertNotNull(lastAlbum.get("copyrights"), "copyrights missing");
        Assert.assertNotNull(lastAlbum.get("external_ids"), "external_ids missing");
        Assert.assertNotNull(lastAlbum.get("genres"), "genres missing");
        Assert.assertNotNull(lastAlbum.get("label"), "label missing");
        Assert.assertNotNull(lastAlbum.get("popularity"), "popularity missing");

        System.out.println("✅ Validated album: " + lastAlbum.get("name"));
    }
}
