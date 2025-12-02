package User;

import data.BaseTestClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class GetCurrentUserWithWrongToken extends BaseTestClass {



    @Test
    public void CheckTheUserAPIReturns401(){
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN + "wrong")
                .when()
                .get("/me")
                .then()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(401)
                .body("error", notNullValue())
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        String error = jsonPath.getString("error");
        System.out.println("Error" + error);
    }

    @Test
    public void testUserProfileFieldsExist() {
        given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN + "wrong")
                .when()
                .get("/me")
                .then()
                .time(lessThan(2000L))
                .statusCode(401)
                .body("error", notNullValue());
    }

}
