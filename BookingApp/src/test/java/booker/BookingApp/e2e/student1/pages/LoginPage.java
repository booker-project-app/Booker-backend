package booker.BookingApp.e2e.student1.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    private static final String EMAIL = "email2@gmail.com";

    private static final String PASSWORD = "12345678";

    @FindBy(css = "input[id='emailField']")
    private WebElement emailField;

    @FindBy(css = "input[id='passwordField']")
    private WebElement passwordField;

    @FindBy(css = "button[id='btnLogin']")
    private WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterEmail() {
        emailField.sendKeys(EMAIL);
        emailField.sendKeys(Keys.ENTER);
    }

    public void enterPassword() {
        passwordField.sendKeys(PASSWORD);
    }

    public void clickLogin() {
        btnLogin.click();
    }

}
