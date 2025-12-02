package data;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.filters;

public class BaseTestClass {

    public DataClass dataClass = new DataClass();

    @BeforeTest
    public void setup(){
        RestAssured.baseURI = dataClass.SPOTIFY_BASE_URL;
        filters(new AllureRestAssured());
    }

    // Add any common methods or configurations for your tests here


}
