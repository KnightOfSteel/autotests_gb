package tests;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RunTests {

    public static RequestSpecification given() {
        return RestAssured.given()
                .config(RestAssured.config()
                        .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));
    }
    @Test
    public void positiveAuthTest() {
        TestData testData = new TestData();
        Gson requestBody = new Gson();
        String requestBodyString = requestBody.toJson(testData); //получение строки json'а из объекта

        Response response = given()
                .relaxedHTTPSValidation()
                .auth().preemptive().basic("1234567890111111111111", "3cd907ac6d") //хотел через объект закинуть, но пока с этим проблема
                .header("Content-Type", "text/plain;charset=UTF-8")
                .post("https://test-stand.gb.ru/gateway/login");
        Assert.assertEquals(200, response.getStatusCode()); //вот тут выдает 500-ую
    }
}
