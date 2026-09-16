package pages;

import org.openqa.selenium.WebDriver;

public class BcLiquorStoresPage extends BasePage {

    private WebDriver driver;

    // Constructor accepts driver passed from the test script
    public BcLiquorStoresPage(WebDriver driver) {
        super(driver); // Chains the driver instance up to BasePage
    }
}
