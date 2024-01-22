package booker.BookingApp.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    private static final String PAGE_URL = "http://localhost:4200/home";

    @FindBy(css = "input.search-button")
    private WebElement searcBtn;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickSearch() {
        searcBtn.click();
    }
}