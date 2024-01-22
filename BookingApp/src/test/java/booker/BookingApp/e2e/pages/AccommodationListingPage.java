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

    @FindBy(css = "app-accommodation-card")
    private List<WebElement> accommodationCards;


    public AccommodationListingPage(WebDriver driver){
        this.driver = driver;
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
        for(WebElement card : accommodationCards) {
            String name = card.findElement(By.cssSelector("b")).getText();
            names.add(name);
        }
        return names;
    }
}
