package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.time.Duration;


public class BaseTest {
        // Protected so child test classes can access this reference
        protected WebDriver driver;


        @BeforeMethod(alwaysRun = true)
        protected void setUp() {
            // Initialize the driver instance
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            //driver.navigate().to("https://www.bcldb.com/");
        }

        @AfterMethod(alwaysRun = true)
        protected void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }

        protected void threadSleep(int seconds){
            try {
                System.out.println(seconds + " seconds wait!");
                Thread.sleep(Duration.ofSeconds(seconds));
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }
        }
}
