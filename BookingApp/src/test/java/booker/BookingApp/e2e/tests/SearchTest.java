package booker.BookingApp.e2e.tests;

import booker.BookingApp.e2e.pages.AccommodationListingPage;
import booker.BookingApp.e2e.pages.HomePage;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchTest  extends TestBase {

    @Test
    public void emptyLocation() {
        HomePage homePage = new HomePage(driver);
        homePage.enterFromDate("3/22/2024");
        homePage.enterToDate("3/25/2024");
        homePage.enterPeople("2");
        homePage.clickSearch();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Please fill all required fields (location, dates and number of guests)");
        alert.accept();
    }

    @Test
    public void emptyDates() {
        HomePage homePage = new HomePage(driver);
        homePage.enterLocation("London");
        homePage.enterPeople("2");
        homePage.clickSearch();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Please fill all required fields (location, dates and number of guests)");
        alert.accept();
    }

    @Test
    public void emptyPeople() {
        HomePage homePage = new HomePage(driver);
        homePage.enterLocation("London");
        homePage.enterFromDate("3/22/2024");
        homePage.enterToDate("3/25/2024");
        homePage.clickSearch();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Number of guests can not be less than 0!");
        alert.accept();
    }

    @Test
    public void pastDates() {
        HomePage homePage = new HomePage(driver);
        homePage.enterLocation("London");
        homePage.enterFromDate("3/22/2023");
        homePage.enterToDate("3/25/2023");
        homePage.enterPeople("2");
        homePage.clickSearch();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "You can not search past dates");
        alert.accept();
    }

    @Test
    public void endDateBeforeStartDate() {
        HomePage homePage = new HomePage(driver);
        homePage.enterLocation("London");
        homePage.enterFromDate("3/22/2024");
        homePage.enterToDate("3/21/2024");
        homePage.enterPeople("2");
        homePage.clickSearch();

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Trip can not end before it starts!");
        alert.accept();
    }

    @Test
    public void validTest() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.enterLocation("London");
        homePage.enterFromDate("3/22/2024");
        homePage.enterToDate("3/25/2024");
        homePage.enterPeople("2");
        homePage.clickSearch();

        AccommodationListingPage listingPage = new AccommodationListingPage(driver);
        Assert.assertTrue(listingPage.isPageOpened());

        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        String[] expectedNames = {"Cozy Cabin", "Example apartment 2", "Villa Relaxation"};
        for(int i = 0; i < names.size(); i++) {
            Assert.assertEquals(expectedNames[i], names.get(i));
        }
    }

    @Test
    public void nothingFound() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.enterLocation("asdlfjasdjfalksdjflk");
        homePage.enterFromDate("3/22/2024");
        homePage.enterToDate("3/25/2024");
        homePage.enterPeople("2");
        homePage.clickSearch();

        AccommodationListingPage listingPage = new AccommodationListingPage(driver);
        Assert.assertTrue(listingPage.isPageOpened());

        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }
}
