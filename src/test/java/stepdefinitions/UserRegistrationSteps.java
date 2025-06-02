package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.chrome.ChromeOptions;
import io.cucumber.java.After;
import static org.junit.Assert.*;
import org.apache.commons.lang3.RandomStringUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class UserRegistrationSteps {

    WebDriver driver;

    @Before
    public void setup() {
        String browser = System.getProperty("browser", "chrome");

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--user-data-dir=C:/Users/palla/Temp/ChromeProfile_" + System.currentTimeMillis());
            driver = new ChromeDriver(options);
        }
    }

    @Given("the user is on the registration page")
    public void i_am_on_registration_page() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("the user enters valid details")
    public void fill_valid_data() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@example.com";
        waitForElement(By.id("dp")).sendKeys("14/12/1978");
        waitForElement(By.id("member_firstname")).sendKeys("John");
        waitForElement(By.id("member_lastname")).sendKeys("Doe");
        waitForElement(By.id("member_emailaddress")).sendKeys(email);
        waitForElement(By.id("member_confirmemailaddress")).sendKeys(email);
        waitForElement(By.id("signupunlicenced_password")).sendKeys("Password123");
        waitForElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Password123");
        waitForElement(By.cssSelector("label[for='sign_up_25']")).click();
        waitForElement(By.cssSelector("label[for='sign_up_26']")).click();
        waitForElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();

    }

    @When("the user enters first name and password but leaves last name blank")
    public void fill_data_except_last_name() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@example.com";
        waitForElement(By.id("dp")).sendKeys("14/12/1978");
        waitForElement(By.id("member_firstname")).sendKeys("John");
        waitForElement(By.id("member_emailaddress")).sendKeys(email);
        waitForElement(By.id("member_confirmemailaddress")).sendKeys("john@example.com");
        waitForElement(By.id("signupunlicenced_password")).sendKeys("Password123");
        waitForElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Password123");
        waitForElement(By.cssSelector("label[for='sign_up_25']")).click();
        waitForElement(By.cssSelector("label[for='sign_up_26']")).click();
        waitForElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();
    }

    @When("the user enters the password and confirmation password incorrectly")
    public void fill_data_with_mismatching_passwords() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@example.com";
        waitForElement(By.id("dp")).sendKeys("14/12/1978");
        waitForElement(By.id("member_firstname")).sendKeys("John");
        waitForElement(By.id("member_lastname")).sendKeys("Doe");
        waitForElement(By.id("member_emailaddress")).sendKeys(email);
        waitForElement(By.id("member_confirmemailaddress")).sendKeys("john@example.com");
        waitForElement(By.id("signupunlicenced_password")).sendKeys("Password123");
        waitForElement(By.id("signupunlicenced_confirmpassword")).sendKeys("DifferentPass");
        waitForElement(By.cssSelector("label[for='sign_up_25']")).click();
        waitForElement(By.cssSelector("label[for='sign_up_26']")).click();
        waitForElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();
    }

    @When("the user does not accept the terms and conditions")
    public void do_not_accept_terms() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@example.com";
        waitForElement(By.id("dp")).sendKeys("14/12/1978");
        waitForElement(By.id("member_firstname")).sendKeys("John");
        waitForElement(By.id("member_lastname")).sendKeys("Doe");
        waitForElement(By.id("member_emailaddress")).sendKeys(email);
        waitForElement(By.id("member_confirmemailaddress")).sendKeys("john@example.com");
        waitForElement(By.id("signupunlicenced_password")).sendKeys("Password123");
        waitForElement(By.id("signupunlicenced_confirmpassword")).sendKeys("DifferentPass");
        waitForElement(By.cssSelector("label[for='sign_up_25']")).click();
        waitForElement(By.cssSelector("label[for='sign_up_26']")).click();
    }

    @When("clicks the register button")
    public void submit_form() {
        waitForElement(By.name("join")).click();
    }

    @Then("the user should be registered successfully")
    public void verify_account_created() {
        WebElement thankYouMsg = waitForElement(By.xpath("//h2[contains(text(),'THANK YOU FOR CREATING AN ACCOUNT')]"));
        assertTrue("Confirmation message not visible!", thankYouMsg.isDisplayed());
        assertTrue("Expected message not found!",
                thankYouMsg.getText().contains("THANK YOU FOR CREATING AN ACCOUNT"));
        driver.quit();
    }

    @Then("the registration should fail with an error message")
    public void error_missing_last_name_or_terms() {
        boolean errorShown = driver.getPageSource().contains("Last Name is required")
                || driver.getPageSource().contains("You must accept the terms and conditions");
        assertTrue("Expected error message not found!", errorShown);
        driver.quit();
    }

    @Then("the registration should fail with a mismatch error message")
    public void error_password_mismatch() {
        assertTrue("Password mismatch error not found!",
                driver.getPageSource().contains("Password did not match"));
        driver.quit();
    }

    private WebElement waitForElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element: " + locator.toString());
            throw e;
        }
    }

    @When("I enter first name {string}")
    public void enter_first_name(String firstName) {
        waitForElement(By.id("member_firstname")).sendKeys(firstName);
    }

    @When("I enter last name {string}")
    public void enter_last_name(String lastName) {
        waitForElement(By.id("member_lastName")).sendKeys(lastName);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed after scenario.");
        }
    }
}
