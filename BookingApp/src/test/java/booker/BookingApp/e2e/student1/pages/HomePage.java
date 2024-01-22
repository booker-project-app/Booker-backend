package booker.BookingApp.e2e.student1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;

    private static final String PAGE_URL = "http://localhost:4200/home";

    @FindBy(css = "button[class='log-in']")
    private WebElement loginBtn;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void selectLogin() {loginBtn.click();}
}
