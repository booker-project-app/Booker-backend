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

    @FindBy(css = "#where_input")
    private WebElement whereInput;

    @FindBy(css = "#from_date")
    private WebElement fromDateInput;

    @FindBy(css = "#to_date")
    private WebElement toDateInput;

    @FindBy(css = "#people_input")
    private WebElement peopleInput;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void enterLocation(String location) {
        whereInput.sendKeys(location);
    }

    public void enterFromDate(String date) {
        fromDateInput.sendKeys(date);
    }

    public void enterToDate(String date) {
        toDateInput.sendKeys(date);
    }

    public void enterPeople(String people) {
        peopleInput.sendKeys(people);
    }

    public void clickSearch() {
        searcBtn.click();
    }
}