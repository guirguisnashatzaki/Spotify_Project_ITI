package Album;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Get_Album extends BaseTestClass {

    @Test
    public void verifyStatusCodeForGetAlbum() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/albums/" + dataClass.ALBUM_ID)
                .then()
                .assertThat()
                .statusCode(200);
    }


    @Test
    public void verifyGettingRequiredAttributes() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/albums/" + dataClass.ALBUM_ID)
                .then()
                .assertThat()
                .statusCode(200)
                .body("album_type", org.hamcrest.Matchers.notNullValue())
                .body("artists", org.hamcrest.Matchers.notNullValue())
                .body("id", org.hamcrest.Matchers.notNullValue())
                .body("name", org.hamcrest.Matchers.notNullValue())
                .body("release_date", org.hamcrest.Matchers.notNullValue())
                .body("total_tracks", org.hamcrest.Matchers.notNullValue())
                .body("tracks", org.hamcrest.Matchers.notNullValue())
                .body("images", org.hamcrest.Matchers.notNullValue())
                .body("available_markets", org.hamcrest.Matchers.notNullValue())
                .body("genres", org.hamcrest.Matchers.notNullValue())
                .body("label", org.hamcrest.Matchers.notNullValue())
                .body("popularity", org.hamcrest.Matchers.notNullValue())
                .body("release_date_precision", org.hamcrest.Matchers.notNullValue())
                .body("type", org.hamcrest.Matchers.notNullValue())
                .body("href", org.hamcrest.Matchers.notNullValue())
                .body("external_urls", org.hamcrest.Matchers.notNullValue())
                .body("uri", org.hamcrest.Matchers.notNullValue());
    }
}