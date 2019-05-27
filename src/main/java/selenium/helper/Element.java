package selenium.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Element {


    private final WebElement element;
    private final WebDriver driver;

    public Element(WebElement element, WebDriver driver) {

        this.element = element;
        this.driver = driver;
    }

    public WebElement seleniumElement() {
        return element;
    }

    public void clear() {
        element.clear();
    }

    public void click() {
        element.click();
    }

    public void sendKeys(String value) {
        element.sendKeys(value);
    }

    public void clearAndSendKeys(String value) {
        element.clear();
        element.sendKeys(value);
    }

    public void setFieldValueThroughJs(Object value) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value=" + value + ";", element);
    }

    public void submit() {
        element.submit();
    }

    public void clickWithJs() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }


}
