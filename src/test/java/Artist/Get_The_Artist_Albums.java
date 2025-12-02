package Artist;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class Get_The_Artist_Albums extends BaseTestClass {

    @Test
    public void verifyStatusCodeForGetTheArtistAlbums() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/artists/"+dataClass.ARTIST_ID+"/albums")
                .then()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void verifyItReturnTheRequiredAttributes() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/artists/"+dataClass.ARTIST_ID+"/albums")
                .then()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(200)
                .body("href", org.hamcrest.Matchers.notNullValue())
                .body("limit", org.hamcrest.Matchers.notNullValue())
                .body("next", org.hamcrest.Matchers.notNullValue())
                .body("previous", org.hamcrest.Matchers.anyOf(org.hamcrest.Matchers.nullValue(), org.hamcrest.Matchers.notNullValue()))
                .body("total", org.hamcrest.Matchers.notNullValue())
                .body("items", org.hamcrest.Matchers.notNullValue())
                .body("offset", org.hamcrest.Matchers.notNullValue());
    }
}