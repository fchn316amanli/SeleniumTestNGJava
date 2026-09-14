package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.HomePage;

import java.time.Duration;


public class BaseTest {
        // Protected so child test classes can access this reference
        protected WebDriver driver;


        @BeforeMethod(alwaysRun = true)
        public void setUp() {
            // Initialize the driver instance
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            //driver.navigate().to("https://www.bcldb.com/");
        }

        @AfterMethod(alwaysRun = true)
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }

        public void threadSleep(int seconds){
            try {
                System.out.println(seconds + " seconds wait!");
                Thread.sleep(Duration.ofSeconds(seconds));
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }
        }
}
