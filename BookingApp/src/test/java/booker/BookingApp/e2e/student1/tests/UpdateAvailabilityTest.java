package booker.BookingApp.e2e.student1.tests;


import booker.BookingApp.e2e.student1.pages.*;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertFalse;

public class UpdateAvailabilityTest extends TestBase{
    @Test
    public void test(){
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();

        OwnerHomePage ownerHomePage = new OwnerHomePage(driver);
        ownerHomePage.clickMenuToggle();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.selectEditAccommodation();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.selectEditPriceAndAvailability();

        UpdateAvailabilityPage updateAvailabilityPage = new UpdateAvailabilityPage(driver);
        updateAvailabilityPage.enterAvailability();
        updateAvailabilityPage.enterPriceDetails();
        updateAvailabilityPage.enterDeadline();
        updateAvailabilityPage.submitData();
        updateAvailabilityPage.refreshPage();
        updateAvailabilityPage.logout();
    }

    @Test
    public void testUnsuccessful() {
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();

        OwnerHomePage ownerHomePage = new OwnerHomePage(driver);
        ownerHomePage.clickMenuToggle();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.selectEditAccommodation();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.selectEditPriceAndAvailability();
        UpdateAvailabilityPage updateAvailabilityPage = new UpdateAvailabilityPage(driver);
        updateAvailabilityPage.enterAvailabilityUnsuccessful();
        updateAvailabilityPage.enterPriceDatesUnsuccessful();
        updateAvailabilityPage.enterDeadlineUnsuccessful();
        updateAvailabilityPage.submitData();
        updateAvailabilityPage.logout();
    }

    @Test
    public void negativeDeadlineTest() {
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();

        OwnerHomePage ownerHomePage = new OwnerHomePage(driver);
        ownerHomePage.clickMenuToggle();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.selectEditAccommodation();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.selectEditPriceAndAvailability();
        UpdateAvailabilityPage updateAvailabilityPage = new UpdateAvailabilityPage(driver);
        updateAvailabilityPage.enterAvailability();
        updateAvailabilityPage.enterPriceDetails();
        updateAvailabilityPage.enterDeadlineNegative();
        boolean submitEnabled = updateAvailabilityPage.checkSubmitButtonEnabled();
        assertFalse(submitEnabled);
        updateAvailabilityPage.logout();
    }

    @Test
    public void negativeAmountTest() {
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();

        OwnerHomePage ownerHomePage = new OwnerHomePage(driver);
        ownerHomePage.clickMenuToggle();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.selectEditAccommodation();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.selectEditPriceAndAvailability();
        UpdateAvailabilityPage updateAvailabilityPage = new UpdateAvailabilityPage(driver);
        updateAvailabilityPage.enterAvailability();
        updateAvailabilityPage.enterNegativePriceAmount();
        updateAvailabilityPage.enterDeadline();
        boolean submitEnabled = updateAvailabilityPage.checkSubmitButtonEnabled();
        assertFalse(submitEnabled);
        updateAvailabilityPage.logout();
    }

    @Test
    public void testWithoutAvailability() {
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();

        OwnerHomePage ownerHomePage = new OwnerHomePage(driver);
        ownerHomePage.clickMenuToggle();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.selectEditAccommodation();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.selectEditPriceAndAvailability();
        UpdateAvailabilityPage updateAvailabilityPage = new UpdateAvailabilityPage(driver);
        updateAvailabilityPage.enterWithoutAvailability();
        updateAvailabilityPage.enterPriceDetails();
        updateAvailabilityPage.enterDeadline();
        boolean submitEnabled = updateAvailabilityPage.checkSubmitButtonEnabled();
        assertFalse(submitEnabled);
        updateAvailabilityPage.logout();
    }

    @Test
    public void testWithoutPriceDates() {
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();

        OwnerHomePage ownerHomePage = new OwnerHomePage(driver);
        ownerHomePage.clickMenuToggle();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.selectEditAccommodation();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.selectEditPriceAndAvailability();
        UpdateAvailabilityPage updateAvailabilityPage = new UpdateAvailabilityPage(driver);
        updateAvailabilityPage.enterAvailability();
        updateAvailabilityPage.enterPriceDetailsWithoutDates();
        updateAvailabilityPage.enterDeadline();
        boolean submitEnabled = updateAvailabilityPage.checkSubmitButtonEnabled();
        assertFalse(submitEnabled);
        updateAvailabilityPage.logout();
    }
}
