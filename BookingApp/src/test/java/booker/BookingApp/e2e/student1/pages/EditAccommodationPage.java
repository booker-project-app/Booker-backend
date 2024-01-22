package booker.BookingApp.e2e.student1.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditAccommodationPage {
    private WebDriver driver;

    @FindBy(css = "div[class='background'] > div[class='dialog1'] > div:nth-child(6) > button[class='btn-more-update']")
    private WebElement btnUpdateMore;

    public EditAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectEditPriceAndAvailability() {
        scrollToElement(driver, btnUpdateMore);
        btnUpdateMore.click();

    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }
}
