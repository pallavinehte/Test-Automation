package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)

public class TestRunner {
    WebDriver driver = new ChromeDriver();

    @Test
    public void testSuccessfulRegistration() {
        driver.get("http://example.com/registration");
        driver.findElement(By.name("firstName")).sendKeys("John");
        driver.findElement(By.name("lastName")).sendKeys("Doe");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.name("confirmPassword")).sendKeys("password123");
        driver.findElement(By.id("terms")).click();
        driver.findElement(By.id("registerButton")).click();

        assertTrue(driver.findElement(By.id("successMessage")).isDisplayed());
    }

    @Test
    public void testRegistrationWithoutLastName() {
        driver.get("http://example.com/registration");

        driver.findElement(By.name("firstName")).sendKeys("John");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.name("confirmPassword")).sendKeys("password123");
        driver.findElement(By.id("registerButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isErrorDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorMessage"))).isDisplayed();

        assertTrue(isErrorDisplayed);
    }

    @Test
    public void testPasswordMismatch() {
        driver.get("http://example.com/registration");
        driver.findElement(By.name("firstName")).sendKeys("John");
        driver.findElement(By.name("lastName")).sendKeys("Doe");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.name("confirmPassword")).sendKeys("password124");
        driver.findElement(By.id("registerButton")).click();

        assertTrue(driver.findElement(By.id("mismatchError")).isDisplayed());
    }

    @Test
    public void testTermsAndConditionsNotAccepted() {
        driver.get("http://example.com/registration");
        driver.findElement(By.name("firstName")).sendKeys("John");
        driver.findElement(By.name("lastName")).sendKeys("Doe");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.name("confirmPassword")).sendKeys("password123");
        driver.findElement(By.id("registerButton")).click();

        assertTrue(driver.findElement(By.id("termsError")).isDisplayed());
    }

}
