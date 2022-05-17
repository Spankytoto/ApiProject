package api.mocks;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Mocks extends MockConfig {

    public static void checkRegister() {
        wireMockServer.stubFor(post("/" + REGISTER)
                      .withRequestBody(equalToJson("{'name': 'Artur'}"))
                      .willReturn(aResponse()
                      .withStatus(205)
                      .withStatusMessage("Success!")));
    }

    public static void checkUnSuccessRegister() {
        wireMockServer.stubFor(post("/" + REGISTER)
                      .withRequestBody(equalToJson("{'name': 'David'}"))
                      .willReturn(aResponse()
                      .withStatus(404)
                      .withStatusMessage("Try again")));
    }

    public static void deleteUserTest () {
        wireMockServer.stubFor(delete("/" + String.format(USER, 2))
                      .willReturn(aResponse()
                      .withStatus(204)));
    }
}
