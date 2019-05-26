package selenium;

import fwk.BugCatcher;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.configuration.SeleniumConfigurator;
import selenium.exceptions.UnsupportedBrowserException;
import selenium.helper.WebDriverHelper;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class SeleniumWrapper {

    private static SeleniumConfigurator configurator;
    private static ThreadLocal<SeleniumWrapper> instance;
    private final BugCatcher bugCatcher;
    private WebDriver driver;
    private static final String CHROME_SWITCHES_CAPABILITY = "chrome.switches";


    private SeleniumWrapper() throws IOException, ParseException, UnsupportedBrowserException {
        bugCatcher = BugCatcher.getInstance();
        configurator = makeConfigurator(bugCatcher.getConfigurator().getSeleniumConfigFilename());
    }

    public static SeleniumWrapper getInstance() throws ParseException, UnsupportedBrowserException, IOException {
        if (instance == null) {
            instance = new ThreadLocal<>();
        }
        if (instance.get() == null) {
            instance.set(new SeleniumWrapper());
        }
        return instance.get();
    }

    public SeleniumConfigurator getConfigurator() {
        return configurator;
    }

    public Boolean driverIsNull() {
        return driver == null;
    }

    public WebDriver getDriver() throws UnsupportedBrowserException, MalformedURLException {

        if (driver == null) {
            Capabilities capabilities = getCapabilities(configurator.getBrowserName());
            driver = make(configurator.getUrl(), configurator.getBrowserName(), capabilities);
        }
        return driver;
    }

    public WebDriverHelper getDriverHelper() throws UnsupportedBrowserException, MalformedURLException {
        return new WebDriverHelper(getDriver(), getConfigurator());
    }


    public void disposeDriver() {
        driver.quit();
        driver = null;
    }

    private static WebDriver make(String url, String browserName, Capabilities capabilities)
            throws MalformedURLException, UnsupportedBrowserException {
        if (configurator.isRemote())
            return new RemoteWebDriver(new URL(url), capabilities);
        else {
            if ("chrome".equals(browserName)) {
                return new ChromeDriver((ChromeOptions) capabilities);
            } else if ("firefox".equals(browserName)) {
                return new FirefoxDriver((FirefoxOptions) capabilities);
            }
            throw new UnsupportedBrowserException(browserName);
        }
    }

    private SeleniumConfigurator makeConfigurator(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return new SeleniumConfigurator((JSONObject) jsonParser.parse(reader));
    }

    private static Capabilities getCapabilities(
            String browserName)
            throws UnsupportedBrowserException {
        if (browserName.equals("chrome")) {
            ChromeOptions capabilities = new ChromeOptions();
            capabilities.addArguments("--start-maximized");
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(CHROME_SWITCHES_CAPABILITY,
                    Arrays.asList("--ignore-certificate-errors"));
            return capabilities;
        } else if (browserName.equals("firefox")) {
            FirefoxOptions capabilities = new FirefoxOptions();
            capabilities.setAcceptInsecureCerts(true);
            return capabilities;
        }

        throw new UnsupportedBrowserException(browserName);
    }
}
