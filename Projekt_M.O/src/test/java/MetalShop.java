import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MetalShop {
        String password = "Tester1234";
        String username = "Tester123";
        static WebDriver driver = new ChromeDriver();
        @BeforeAll
        public static void setUp() {
            driver.manage().window().maximize();
            driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        }
        @AfterAll
        public static void closeBrowser() {
            driver.quit();
        }

       @Test
        public void emptyLogin () {
            driver.findElement(By.linkText("Moje konto")).click();
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.name("login")).click();
            String error = driver.findElement(By.cssSelector(".woocommerce-error")).getText();
           Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", error);
        }
        @Test
        public void emptyPassword() {
            driver.findElement(By.linkText("Moje konto")).click();
            driver.findElement(By.id("username")).sendKeys(username);
            driver.findElement(By.name("login")).click();
            String error = driver.findElement(By.cssSelector(".woocommerce-error")).getText();
            Assertions.assertEquals("Błąd: pole hasła jest puste.", error);
        }
        @Test
        public void registerSuccess() {
            String password = "Test1234";
            driver.findElement(By.linkText("register")).click();
            Faker faker = new Faker();
            String  registerUsername = faker.name().username();
            String email = registerUsername + faker.random().nextInt(10000) + "@wp.pl";
            driver.findElement(By.cssSelector("#user_login")).sendKeys(registerUsername);
            driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
            driver.findElement(By.cssSelector("#user_pass")).sendKeys(password);
            driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
            driver.findElement(By.cssSelector(".ur-submit-button")).click();
            Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".user-registration-message")));
            WebElement error = driver.findElement(By.cssSelector(".user-registration-message"));
            Assertions.assertEquals("User successfully registered.", error.getText());
        }
        @Test
        public void BackToMainPage () {
            driver.findElement(By.linkText("Moje konto")).click();
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("username")).sendKeys(username);
            driver.findElement(By.name("login")).click();
            driver.findElement(By.linkText("Strona główna")).click();
            Assertions.assertEquals("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/", driver.getCurrentUrl());
    }
    @Test
    public void goToContact() {
        driver.findElement(By.linkText("Kontakt")).click();
        Assertions.assertEquals("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/kontakt/", driver.getCurrentUrl());
    }
    @Test
    public void sendingAMessage()  {
            String dane =  "Anna Kowalska";
            String email = "martyna@wp.pl";
        driver.findElement(By.linkText("Kontakt")).click();
        driver.findElement(By.cssSelector(".wpcf7-form-control")).sendKeys(dane);
        driver.findElement(By.cssSelector(".wpcf7-validates-as-email")).sendKeys(email);
        driver.findElement(By.cssSelector(".wpcf7-submit")).click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wpcf7-response-output")));
        Assertions.assertEquals("Twoja wiadomość została wysłana. Dziękujemy!", driver.findElement
                (By.cssSelector(".wpcf7-response-output")).getText());
    }
    @Test
    public void addProductToCart()  {
        driver.findElement(By.cssSelector(".add_to_cart_button")).click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.linkText("Koszyk")).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".remove")));
        driver.findElement(By.cssSelector(".remove")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".woocommerce-message")));
        Assertions.assertEquals("Usunięto: „Srebrna moneta 5g - UK 1980”. Cofnij?", driver.findElement
                (By.cssSelector(".woocommerce-message")).getText());
    }

}


