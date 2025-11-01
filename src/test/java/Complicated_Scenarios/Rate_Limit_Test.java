package Complicated_Scenarios;

import data.BaseTestClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Rate_Limit_Test extends BaseTestClass {


    @Test
    public void testRateLimitingAndRetry() {
        int attempts = 0;
        boolean success = false;

        while (attempts < 5 && !success) {
            Response response = given()
                    .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                    .when()
                    .get("/me/top/tracks?limit=50")
                    .andReturn();

            if (response.statusCode() == 200) {
                success = true;
            } else if (response.statusCode() == 429) {
                int wait = response.getHeader("Retry-After") != null ?
                        Integer.parseInt(response.getHeader("Retry-After")) : 1;
                try { Thread.sleep(wait * 1000L); } catch (InterruptedException ignored) {}
            }
            attempts++;
        }

        Assert.assertTrue(success, "API did not recover after retries");
    }


}
