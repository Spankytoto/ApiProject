package api.tests;

import api.mocks.Mocks;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class MockTest extends Mocks {

    @Test
    public static void checkRegister() {
        Mocks.checkRegister();

        HashMap<String, String> user = new HashMap();
        user.put("name", "Artur");

        Response response = given()
                            .body(user)
                            .when()
                            .post(MOCK_URL + REGISTER);

        Assert.assertEquals(response.statusCode(), 205);
        Assert.assertTrue(response.getStatusLine().contains("Success!"));
    }

    @Test
    public static void checkUnSuccessRegister() {
        Mocks.checkUnSuccessRegister();

        HashMap<String, String> user = new HashMap();
        user.put("name", "David");

        Response response = given()
                            .body(user)
                            .when()
                            .post(MOCK_URL + REGISTER);

        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertTrue(response.getStatusLine().contains("Try again"));
    }

    @Test
    public static void deleteUserTest () {
        Mocks.deleteUserTest();

        Response response = given()
                            .when()
                            .delete(MOCK_URL + String.format(USER, 2));

        Assert.assertEquals(response.statusCode(), 204);
    }
}
