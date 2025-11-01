package Track;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Get_Track extends BaseTestClass {

    @Test
    public void verifyStatusCodeForGetTrack() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/tracks/" + dataClass.TRACK_ID)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void verifyGettingTheRequiredAttributes() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/tracks/" + dataClass.TRACK_ID)
                .then()
                .assertThat()
                .statusCode(200)
                .body("album", org.hamcrest.Matchers.notNullValue())
                .body("artists", org.hamcrest.Matchers.notNullValue())
                .body("id", org.hamcrest.Matchers.notNullValue())
        ;
    }
}
