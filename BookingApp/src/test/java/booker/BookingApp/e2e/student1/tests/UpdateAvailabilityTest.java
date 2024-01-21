package booker.BookingApp.e2e.student1.tests;


import booker.BookingApp.e2e.student1.pages.HomePage;
import booker.BookingApp.e2e.student1.pages.LoginPage;
import org.testng.annotations.Test;

public class UpdateAvailabilityTest extends TestBase{
    @Test
    public void test() {
        HomePage homePage = new HomePage(driver);
        homePage.selectLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail();
        loginPage.enterPassword();
        loginPage.clickLogin();
    }
}
