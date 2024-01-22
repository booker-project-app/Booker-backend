package booker.BookingApp.e2e.student1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OwnerHomePage {
    private WebDriver driver;

    @FindBy(css = "button[class = 'menu-toggle']")
    private WebElement menuToggle;

    @FindBy(css = "ul[id='menu']")
    private WebElement showRightMenu;

    @FindBy(css = "ul[id='menu'] > li:nth-child(3) > button")
    private WebElement myAccommodations;


    public OwnerHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickMenuToggle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(menuToggle));
        menuToggle.click();
        wait.until(ExpectedConditions.visibilityOf(showRightMenu));
        myAccommodations.click();
    }
}
