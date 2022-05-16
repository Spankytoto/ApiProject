package api.test;

import api.core.BaseUrl;
import api.mocks.MockServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class ReqresMock extends MockServer implements BaseUrl {

    @Test
    public void checkRegister() {
        HashMap<String, String> user = new HashMap();
        user.put("name", "Artur");

        wireMockServer.stubFor(post("/" + REGISTER)
                      .withRequestBody(equalToJson("{'name': 'Artur'}"))
                      .willReturn(aResponse()
                      .withStatus(205)
                      .withStatusMessage("Success!")));

        Response response = given()
                           .body(user)
                           .when()
                           .post(MOCK_URL + REGISTER);

        Assert.assertEquals(response.statusCode(), 205);
        Assert.assertTrue(response.getStatusLine().contains("Success!"));
    }

    @Test
    public void checkUnSuccessRegister() {
        HashMap<String, String> user = new HashMap();
        user.put("name", "David");

        wireMockServer.stubFor(post("/" + REGISTER)
                      .withRequestBody(equalToJson("{'name': 'David'}"))
                      .willReturn(aResponse()
                      .withStatus(404)
                      .withStatusMessage("Try again")));

        Response response = given()
                            .body(user)
                            .when()
                            .post(MOCK_URL + REGISTER);

        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertTrue(response.getStatusLine().contains("Try again"));
    }

    @Test
    public void deleteUserTest () {
        wireMockServer.stubFor(delete("/" + String.format(USER, 2))
                      .willReturn(aResponse()
                      .withStatus(204)));

       RestAssured.given()
                  .when()
                  .delete(MOCK_URL + String.format(USER, 2))
                  .then()
                  .statusCode(204);

    }



}
