package api.mocks;

import api.core.BaseUrl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class MockConfig implements BaseUrl {

    public static WireMockServer wireMockServer;

    @BeforeClass(alwaysRun = true)
    public void startMockServer () {
        wireMockServer = new WireMockServer(9060);
        wireMockServer.start();
    }

    @AfterClass(alwaysRun = true)
    public void stopMockServer () {
        wireMockServer.stop();
    }
}
