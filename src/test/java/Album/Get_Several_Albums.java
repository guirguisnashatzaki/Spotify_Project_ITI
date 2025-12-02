package Album;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class Get_Several_Albums extends BaseTestClass {


    @Test
    public void verifyStatusCodeForGetSeveralAlbums() {
        given().when()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .get("/albums?ids="+dataClass.Album_Ids_To_Get)
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
                .get("/albums?ids="+dataClass.Album_Ids_To_Get)
                .then()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(200)
                .body("albums", org.hamcrest.Matchers.notNullValue());
    }

}
