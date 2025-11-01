package User;

import data.BaseTestClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import data.DataClass;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetCurrentUserWithWrongToken extends BaseTestClass {



    @Test
    public void CheckTheUserAPIReturns401(){
        Response response = given()
                .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN + "wrong")
                .when()
                .get("/me")
                .then()
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
                .statusCode(401)
                .body("error", notNullValue());
    }

}
