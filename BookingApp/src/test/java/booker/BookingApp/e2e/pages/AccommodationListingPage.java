package booker.BookingApp.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class AccommodationListingPage {

    private WebDriver driver;

    @FindBy(css = "input.search-button")
    private WebElement searcBtn;

    @FindBy(css = "#min_price")
    private WebElement min_price;

    @FindBy(css = "#max_price")
    private WebElement max_price;

    @FindBy(css = "app-accommodation-card")
    private List<WebElement> accommodationCards;

    @FindBy(css = ".filter-text")
    private List<WebElement> amenitiesAndTypes;


    public AccommodationListingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AccommodationListingPage(WebDriver driver, String url){
        this.driver = driver;
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() throws InterruptedException {
        WebElement isOpened = (new WebDriverWait(driver, Duration.ofSeconds(3)))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".filters")));
        Thread.sleep(200);
        return isOpened.isDisplayed();
    }

    public List<String> getAllAccommodationNames() {
        ArrayList<String> names = new ArrayList<>();
        accommodationCards = driver.findElements(By.cssSelector("app-accommodation-card"));
        for(WebElement card : accommodationCards) {
            String name = card.findElement(By.cssSelector("b")).getText();
            names.add(name);
        }
        return names;
    }

    public void clickOnAmenity(int index) {
        WebElement amenity = amenitiesAndTypes.get(index);
        WebElement checkBox = amenity.findElement(By.xpath("./.."));
        checkBox.click();
    }

    public void clickOnSearch() {
        searcBtn.click();
    }

    public void clickOnType(int index) {
        WebElement amenity = amenitiesAndTypes.get(index+4); //because there are four amenities
        WebElement checkBox = amenity.findElement(By.xpath("./.."));
        checkBox.click();
    }

    public void enterMinPrice(String price) {
        min_price.sendKeys(price);
    }

    public void enterMaxPrice(String price) {
        max_price.sendKeys(price);
    }
}
