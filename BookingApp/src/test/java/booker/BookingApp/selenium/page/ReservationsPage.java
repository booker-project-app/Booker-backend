package booker.BookingApp.selenium.page;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ReservationsPage {
    private WebDriver driver;

    private static final String PAGE_URL = "http://localhost:4200/reservations/guest";

    @FindBy(css = ".card_div")
    List<WebElement> reservationsCards;


    public ReservationsPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void start() throws InterruptedException {
        Thread.sleep(3000);
    }

    public int listSize() {
        return reservationsCards.size();
    }

    public int canceledListSize() {
        int size = 0;
        for (WebElement card : reservationsCards) {
            if (card.findElement(By.cssSelector(".status")).getText().equals("status: CANCELED")) {
                size++;
            }
        }
        return size;
    }

    public boolean cancelReservation(int i) throws InterruptedException {
        WebElement card = reservationsCards.get(i);
        WebElement status = card.findElement(By.cssSelector(".status"));
        if (status.getText().equals("status: ACCEPTED")) {
            WebElement cancelBtn = card.findElement(By.cssSelector("#cancelBtn"));
            cancelBtn.click();
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Tekst alerta: " + alertText);
            alert.accept();
            Thread.sleep(500);
            if (alertText.equals("this request has a past date so you can't cancel it")){
                return false;
            } else if (alertText.equals("You can not cancel reservation because deadline for \" +\n" +
                    "                \"cancellation expired.\\nUnfortunately you must pay for it.")) {
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    public String getAlertMessageText(int i) throws InterruptedException {
        WebElement card = reservationsCards.get(i);
        WebElement status = card.findElement(By.cssSelector(".status"));
        if (status.getText().equals("status: ACCEPTED")) {
            WebElement cancelBtn = card.findElement(By.cssSelector("#cancelBtn"));
            cancelBtn.click();
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            Thread.sleep(500);
            return alertText;
        }
        else {
            return " ";
        }
    }
}
