package booker.BookingApp.e2e.student1.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UpdateAvailabilityPage {
    private WebDriver driver;

    private static final String AMOUNT = "150";

    private static final String DEADLINE = "3";
    private static final String NEGATIVE_DEADLINE = "-10";
    private static final String NEGATIVE_AMOUNT = "-100";

    @FindBy(css = "div[class='background'] > div[class='container'] > form:nth-child(2) > mat-vertical-stepper > div > div:nth-child(2) > div > div > form > mat-form-field > div > div:nth-child(2) > div > mat-datepicker-toggle > button")
    private WebElement availabilityDatesBtn;

    @FindBy(css = "mat-calendar[id='mat-datepicker-0']")
    private WebElement datePickerAvailability;

    @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-0']/mat-calendar-header/div/div/button[3]")
    private WebElement nextMonth;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-0']/mat-calendar-header/div/div/button[1]/span[2]/span")
     private WebElement monthSpan;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-0']/div/mat-month-view/table/tbody/tr[3]/td[6]/button/span[1]")
     private WebElement availabilityStartDate;

     @FindBy(xpath = "//*[@id='mat-date-range-input-0']/div/div[1]/input/span")
     private WebElement startDateSpan;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-0']/div/mat-month-view/table/tbody/tr[6]/td[3]/button/span[1]")
     private WebElement availabilityEndDate;

     @FindBy(css="button[id='nextStepAvailability']")
     private WebElement nextStepAvailability;

     @FindBy(css = "form[id='formGroupPrice']")
     private WebElement formGroupPrice;

     @FindBy(css = "div[class='background'] > div[class='container'] > form:nth-child(2) > mat-vertical-stepper > div:nth-child(2) > div:nth-child(2) > div > div > form > mat-form-field > div > div:nth-child(2) > div > mat-datepicker-toggle > button")
     private WebElement datePickerPriceBtn;

     @FindBy(css = "mat-calendar[id='mat-datepicker-1']")
     private WebElement datePickerPrice;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-1']/mat-calendar-header/div/div/button[3]")
     private WebElement priceNextMonth;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-1']/mat-calendar-header/div/div/button[1]/span[2]/span")
     private WebElement priceNextMonthSpan;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-1']/div/mat-month-view/table/tbody/tr[4]/td[1]/button/span[1]")
     private WebElement priceStartDate;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-1']/div/mat-month-view/table/tbody/tr[6]/td[3]/button/span[1]")
     private WebElement priceEndDate;

     @FindBy(xpath = "//input[@id='amount']")
     private WebElement priceAmount;

     @FindBy(css = "mat-radio-button[value='PER_ACCOMMODATION']")
     private WebElement rbPerAccommodation;

     @FindBy(css = "button[id='nextStepPrice']")
     private WebElement nextStepPrice;

     @FindBy(css = "form[id='formGroupDeadline']")
     private WebElement formGroupDeadline;

     @FindBy(css = "input[id='deadline']")
     private WebElement deadlineText;

     @FindBy(css = "button[id='nextStepDeadline']")
     private WebElement nextStepDeadline;

     @FindBy(css = "div[id='buttons']")
     private WebElement buttonsDiv;

     @FindBy(css = "button[id = 'submitBtn']")
     private WebElement submitBtn;

     @FindBy(css = "form[id='formGroupAvailability']")
     private WebElement formGroupAvailability;

     @FindBy(css = "div[id='mat-snack-bar-container-live-1'] > div > simple-snack-bar > div")
     private WebElement snackBar;

     @FindBy(css = "div[id='mat-snack-bar-container-live-1']")
     private WebElement snackBarDiv;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-0']/div/mat-month-view/table/tbody/tr[3]/td[2]/button/span[1]")
     private WebElement availabilityStartDateUnsuccessful;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-0']/div/mat-month-view/table/tbody/tr[3]/td[4]/button/span[1]")
     private WebElement availabilityEndDateUnsuccessful;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-1']/div/mat-month-view/table/tbody/tr[3]/td[2]/button/span[1]")
     private WebElement priceStartDateUnsuccessful;

     @FindBy(xpath = "//mat-calendar[@id='mat-datepicker-1']/div/mat-month-view/table/tbody/tr[3]/td[4]/button/span[1]")
     private WebElement priceEndDateUnsuccessful;

    @FindBy(css = "button[class = 'menu-toggle']")
    private WebElement menuToggle;

    @FindBy(css = "ul[id='menu']")
    private WebElement showRightMenu;

    @FindBy(css = "ul[id='menu'] > li:nth-child(9) > button")
    private WebElement logoutBtn;

    @FindBy(xpath = "//div[@id='cdk-overlay-2']/mat-snack-bar-container")
    private WebElement snackBarContainer;

    //*[@id="mat-snack-bar-container-live-1"]/div/simple-snack-bar/div[1]
    //*[@id="mat-snack-bar-container-live-1"]/div/simple-snack-bar
    public UpdateAvailabilityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterAvailability() {
        availabilityDatesBtn.click();
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(datePickerAvailability));
        wait.until(ExpectedConditions.elementToBeClickable(datePickerAvailability));
//        wait.until(ExpectedConditions.visibilityOf(nextMonth));
//        wait.until(ExpectedConditions.elementToBeClickable(nextMonth));
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        nextMonth.click();
        wait.until(ExpectedConditions.textToBePresentInElement(monthSpan, "DEC 2024"));
        availabilityStartDate.click();
        availabilityEndDate.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepAvailability.click();


    }

   public void enterPriceDetails() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupPrice));
        datePickerPriceBtn.click();
        wait.until(ExpectedConditions.visibilityOf(datePickerPrice));
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        wait.until(ExpectedConditions.textToBePresentInElement(priceNextMonthSpan, "DEC 2024"));
        priceStartDate.click();
        priceEndDate.click();
        priceAmount.clear();
        priceAmount.sendKeys(AMOUNT);
        rbPerAccommodation.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepPrice.click();
   }

   public void enterDeadline() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupDeadline));
        deadlineText.clear();
        deadlineText.sendKeys(DEADLINE);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepDeadline.click();
   }

   public void submitData() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(buttonsDiv));
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        submitBtn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(snackBarContainer));
   }

    public void refreshPage() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().refresh();
    }

    public void logout() {
        menuToggle.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(showRightMenu));
        logoutBtn.click();
    }

    public void enterAvailabilityUnsuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupAvailability));
        availabilityDatesBtn.click();
        wait.until(ExpectedConditions.visibilityOf(datePickerAvailability));
        nextMonth.click();
        wait.until(ExpectedConditions.textToBePresentInElement(monthSpan, "FEB 2024"));
        availabilityStartDateUnsuccessful.click();
        availabilityEndDateUnsuccessful.click();
        nextStepAvailability.click();
    }

    public void enterPriceDatesUnsuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupPrice));
        datePickerPriceBtn.click();
        wait.until(ExpectedConditions.visibilityOf(datePickerPrice));
        priceNextMonth.click();
        wait.until(ExpectedConditions.textToBePresentInElement(priceNextMonthSpan, "FEB 2024"));
        priceStartDateUnsuccessful.click();
        priceEndDateUnsuccessful.click();
        priceAmount.clear();
        priceAmount.sendKeys(AMOUNT);
        rbPerAccommodation.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepPrice.click();
    }

    public void enterDeadlineUnsuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupDeadline));
        deadlineText.clear();
        deadlineText.sendKeys(DEADLINE);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepDeadline.click();
    }

    public void submitDataUnsuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(buttonsDiv));
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        submitBtn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(snackBarContainer));

    }

    public void enterDeadlineNegative() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupDeadline));
        deadlineText.clear();
        deadlineText.sendKeys(NEGATIVE_DEADLINE);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepDeadline.click();
    }

    public void enterNegativePriceAmount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupPrice));
        datePickerPriceBtn.click();
        wait.until(ExpectedConditions.visibilityOf(datePickerPrice));
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        priceNextMonth.click();
        wait.until(ExpectedConditions.textToBePresentInElement(priceNextMonthSpan, "DEC 2024"));
        priceStartDate.click();
        priceEndDate.click();
        priceAmount.clear();
        priceAmount.sendKeys(NEGATIVE_AMOUNT);
        rbPerAccommodation.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepPrice.click();
    }

    public boolean checkSubmitButtonEnabled() {
        if (submitBtn.isEnabled()) {
            System.out.println("Submit button is enabled");
            return true;
        } else {
            System.out.println("Submit button is disabled");
            return false;
        }
    }

    public void enterWithoutAvailability() {
        nextStepAvailability.click();

    }

    public void enterPriceDetailsWithoutDates() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(formGroupPrice));
        priceAmount.clear();
        priceAmount.sendKeys(AMOUNT);
        rbPerAccommodation.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        nextStepPrice.click();
    }

}
