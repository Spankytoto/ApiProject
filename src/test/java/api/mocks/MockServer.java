package api.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MockConfig {

    private WireMockRule wireMockRule;
    private WireMockServer wireMockServer;
    private WireMockConfiguration wireMockConfiguration;

    @Test
    public void checkThatMockServerWorks() {

    }

    @BeforeMethod
    public void startMockServer () {
        wireMockServer = new WireMockServer(Options.DYNAMIC_PORT);
        wireMockServer.start();

    }

    @AfterMethod
    public void stopMockServer () {
        wireMockServer.stop();

    }

}
