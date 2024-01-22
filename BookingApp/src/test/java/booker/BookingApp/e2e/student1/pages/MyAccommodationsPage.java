package booker.BookingApp.e2e.student1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccommodationsPage {
    private WebDriver driver;

    @FindBy(css = "h2[id='accepted_accommodation']")
    private WebElement headingAccepted;

    @FindBy(css = "h2[id='my_accommodation']")
    private WebElement headingMyAccommodations;

    @FindBy(css = "b[id='acc-name']")
    private WebElement accommodationName;



    @FindBy(css = "button > img[alt='Edit']")
    private WebElement editBtn;


    public MyAccommodationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectEditAccommodation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(headingAccepted, "Accepted accommodation"));
        wait.until(ExpectedConditions.textToBePresentInElement(headingMyAccommodations, "My accommodation"));
        wait.until(ExpectedConditions.textToBePresentInElement(accommodationName, "Cozy Cabin"));
        editBtn.click();
    }
}
