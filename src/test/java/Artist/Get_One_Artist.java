package Artist;

import data.BaseTestClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class Get_One_Artist extends BaseTestClass {

    @Test
    public void verifyStatusCodeForGetOneArtist() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/artists/" + dataClass.ARTIST_ID)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void verifyArtistNameAndIdAndType() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/artists/" + dataClass.ARTIST_ID)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", notNullValue())
                .body("type", notNullValue());
    }

}
