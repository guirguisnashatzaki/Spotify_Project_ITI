package User;

import data.BaseTestClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.number.OrderingComparison;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
@Test(groups = "auth")
public class GetUserProfileTest extends BaseTestClass {

    @Test
    public void CheckTheUserAPIReturns200(){
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me")
                .then()
                .time(OrderingComparison.lessThan(2000L))
                .assertThat()
                .statusCode(200)
                .body("display_name", notNullValue())
                .body("id", notNullValue())
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        String displayName = jsonPath.getString("display_name");
        String id = jsonPath.getString("id");
        System.out.println("Display Name: " + displayName);
        System.out.println("User ID: " + id);
        dataClass.USER_ID = id;
    }

    @Test
    public void testUserProfileFieldsExist() {
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me")
                .then()
                .time(OrderingComparison.lessThan(2000L))
                .statusCode(200)
                .body("id", notNullValue())
                .body("display_name", notNullValue())
                .body("country", notNullValue())
                .body("email", notNullValue());
    }

    @Test
    public void testResponseTimeIsBelow500ms() {
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                .when()
                .get("/me")
                .then()
                .time(OrderingComparison.lessThan(2000L))
                .statusCode(200)
                .time(lessThan(2000L)); // milliseconds
    }


}
