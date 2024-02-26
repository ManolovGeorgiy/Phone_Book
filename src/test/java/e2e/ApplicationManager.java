package e2e;

import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {
    private final Config config = new Config();
    public WebDriver driver;


    protected void init() {
        if (config.getSelenoidState()) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("120.0");
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVNC", false);

            capabilities.setCapability("selenoid:options", selenoidOptions);
            try {
                driver = new RemoteWebDriver(
                        URI.create(config.getSelenoidUrl()).toURL(),
                        capabilities
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.get(config.getProjectUrl());
        driver.manage().window().setSize(new Dimension(config.getWindowWight(), config.getWindowHeight()));

    }

    protected void stop() {
        driver.quit();

    }
}