package booker.BookingApp.e2e.student1.tests;


import booker.BookingApp.e2e.student1.pages.*;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.time.Duration;

@Transactional
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
    }
}
