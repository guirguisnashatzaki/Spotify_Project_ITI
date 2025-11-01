package Complicated_Scenarios;

import data.BaseTestClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

public class New_Releases_Test extends BaseTestClass {

    @Test
    public void validateLocalizationAcrossMarkets() {
        String[] markets = {"US", "DE", "JP", "EG", "BR"};

        for (String market : markets) {
            given()
                    .header("Authorization", "Bearer " + dataClass.ACCESS_TOKEN)
                    .queryParam("country", market)
                    .queryParam("limit", 5)
                    .when()
                    .get("/browse/new-releases")
                    .then()
                    .statusCode(200)
                    .body("albums.items[0].available_markets", hasItem(market))
                    .body("albums.items.size()", greaterThan(0));
        }
    }


}
