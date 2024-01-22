package booker.BookingApp.e2e.tests;

import booker.BookingApp.e2e.pages.HomePage;
import org.testng.annotations.Test;

public class SearchTest  extends TestBase {
    @Test
    public void clickSearchNotingInputted() {
        HomePage home = new HomePage(driver);
        home.clickSearch();
    }
}
