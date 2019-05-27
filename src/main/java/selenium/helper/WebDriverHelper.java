package selenium.helper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import selenium.configuration.SeleniumConfigurator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class WebDriverHelper {

    private final WebDriver driver;
    private final int timeout;
    private final int polling;

    public WebDriverHelper(WebDriver driver, SeleniumConfigurator configurator) {
        timeout = (int) configurator.getTimeout();
        polling = (int) configurator.getPolling();
        this.driver = driver;
    }

    public Element element(
            final By by) {
        return element(by, timeout, polling);
    }

    protected List<Element> elements(
            final By by) {
        return elements(by, timeout, polling);
    }


    public Element element(
            final By by,
            int timeoutDuration,
            int pollingDuration) {
        return (Element) find(
                timeoutDuration,
                pollingDuration,
                webDriver -> {
                    WebElement element = webDriver.findElement(by);
                    if (element == null) {
                        throw new NoSuchElementException(by.toString());
                    }
                    if (element.isDisplayed() && element.isEnabled()) {

                        try {
                            return new Element(element, webDriver);
                        } catch (Exception e) {
                            throw new ElementNotVisibleException(by.toString());
                        }

                    }
                    throw new ElementNotVisibleException(by.toString());
                });
    }

    public List<Element> elements(
            final By by,
            int timeoutDuration,
            int pollingDuration) {
        return (List<Element>) find(
                timeoutDuration,
                pollingDuration,
                webDriver -> {
                    List<WebElement> elements = webDriver.findElements(by);
                    if (elements == null) {
                        throw new NoSuchElementException(by.toString());
                    }

                    try {
                        List<Element> list = new ArrayList<>();
                        for (WebElement element : webDriver.findElements(by)) {
                            list.add(new Element(element, webDriver));
                        }
                        return list;
                    } catch (Exception e) {
                        throw new ElementNotVisibleException(by.toString());
                    }


                });
    }

    private Boolean waitUntil(
            Function<WebDriver, Boolean> expectedCondition,
            int timeoutInSeconds,
            int pollingDuration) {
        return (Boolean) find(timeoutInSeconds, pollingDuration, expectedCondition);
    }


    public Boolean waitUntilPageLoad(
            By by,
            int timeoutInSeconds,
            int pollingDuration) {
        return waitUntil(webDriver -> {
            try {
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                return executor.executeScript("return document.readyState")
                        .toString()
                        .equals("complete");
            } catch (Throwable throwable) {
                return false;
            }
        }, timeoutInSeconds, pollingDuration);
    }

    public Boolean waitUntilPageLoad() {
        return waitUntil(webDriver -> {
            try {
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                return executor.executeScript("return document.readyState")
                        .toString()
                        .equals("complete");
            } catch (Throwable throwable) {
                return false;
            }
        }, timeout, polling);
    }


    public Boolean waitUntilElementAttributeToBeNotEmpty(
            By by, String attribute) {
        return waitUntil(webDriver -> {
            try {
                return ExpectedConditions
                        .attributeToBeNotEmpty(element(by).seleniumElement(), attribute)
                        .apply(webDriver);
            } catch (Throwable throwable) {
                return false;
            }
        }, timeout, polling);
    }

    public Boolean waitUntilElementAttributeToBeNotEmpty(
            By by, String attribute, int timeoutInSeconds,
            int pollingDuration) {

        return waitUntil(webDriver -> {
            try {
                return ExpectedConditions
                        .attributeToBeNotEmpty(element(by).seleniumElement(), attribute)
                        .apply(webDriver);
            } catch (Throwable throwable) {
                return false;
            }
        }, timeoutInSeconds, pollingDuration);
    }

    public Boolean waitUntilElementAttributeToBeEmpty(
            By by, String attribute, int timeoutInSeconds,
            int pollingDuration) {

        return waitUntil(webDriver -> {
            try {
                return ExpectedConditions
                        .attributeToBe(element(by).seleniumElement(), attribute, "")
                        .apply(webDriver);
            } catch (Throwable throwable) {
                return false;
            }
        }, timeoutInSeconds, pollingDuration);
    }

    public Boolean waitUntilElementAttributeToBeEmpty(
            By by, String attribute) {

        return waitUntil(webDriver -> {
            try {
                return ExpectedConditions
                        .attributeToBe(element(by).seleniumElement(), attribute, "")
                        .apply(webDriver);
            } catch (Throwable throwable) {
                return false;
            }
        }, timeout, polling);
    }

    public Boolean waitUntilTextToBePresentInElement(By by,
                                                     String textValue) {

        return waitUntil(webDriver -> {
            try {
                return ExpectedConditions
                        .textToBePresentInElement(element(by).seleniumElement(), textValue)
                        .apply(webDriver);
            } catch (Throwable throwable) {
                return false;
            }
        }, timeout, polling);
    }


    public Boolean waitUntilTextToBePresentInElement(
            By by, String textValue, int timeoutInSeconds,
            int pollingDuration) {

        return waitUntil(webDriver -> {
            try {

                return ExpectedConditions
                        .textToBePresentInElement(element(by).seleniumElement(), textValue)
                        .apply(webDriver);
            } catch (Throwable throwable) {
                return false;
            }
        }, timeoutInSeconds, pollingDuration);
    }

    public Boolean waitUntilElementToBePresent(
            By by, int timeoutInSeconds,
            int pollingDuration) {

        return waitUntil(webDriver -> {
            try {

                return element(by).seleniumElement().isDisplayed() && element(by).seleniumElement().isEnabled();
            } catch (Throwable throwable) {
                return false;
            }
        }, timeoutInSeconds, pollingDuration);
    }

    public Boolean waitUntilElementToBePresent(
            By by) {

        return waitUntil(webDriver -> {
            try {

                return element(by).seleniumElement().isDisplayed() && element(by).seleniumElement().isEnabled();
            } catch (Throwable throwable) {
                return false;
            }
        }, timeout, polling);
    }


    public Element elementWaitUntil(
            By by,
            Function<WebDriver, Element> expectedCondition,
            int timeoutInSeconds,
            int pollingDuration) {
        return (Element) find(timeoutInSeconds, pollingDuration, expectedCondition);
    }

    public Element elementWaitUntil(
            By by,
            Function<WebDriver, Element> expectedCondition) {
        return elementWaitUntil(by, expectedCondition, timeout, polling);
    }

    public Element elementWaitUntilClickable(By by,
                                             int timeoutInSeconds,
                                             int pollingDuration) {

        return elementWaitUntil(
                by,
                webDriver -> (Element) ExpectedConditions
                        .elementToBeClickable(element(by).seleniumElement())
                        .apply(webDriver),
                timeoutInSeconds,
                pollingDuration);
    }

    public Element elementWaitUntilClickable(By by) {
        return elementWaitUntilClickable(by, timeout, polling);
    }


    private <T> Object find(
            int timeoutDuration,
            int pollingDuration,
            Function<WebDriver, T> condition) {

        Object target = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutDuration))
                .pollingEvery(Duration.ofSeconds(pollingDuration))
                .ignoring(NotFoundException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(condition);

        if (target == null) {
            throw new NoSuchElementException();
        }
        return target;
    }


}
