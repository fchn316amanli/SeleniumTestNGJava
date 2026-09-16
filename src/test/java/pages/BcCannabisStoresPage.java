package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.beans.Visibility;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BcCannabisStoresPage extends BasePage {
    /*
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor accepts driver passed from the test script
    // Initialize driver and wait in the constructor
    public BcCannabisStoresPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    */

    public BcCannabisStoresPage(WebDriver driver) {
        super(driver); // Chains the driver instance up to BasePage
    }

    // Alert Dialog, need to dismiss in the beginning
    private By alertDlg = By.id("shopify-pc__banner");
    private By alertDlgH = By.id("shopify-pc__banner__body-title");
    private By alertDlgP = By.cssSelector("#shopify-pc__banner > div > div.shopify-pc__banner__body > p");
    private By alertDlgBtnMgmPrefs = By.id("shopify-pc__banner__btn-manage-prefs");
    private By alertDlgBtnAccept = By.id("shopify-pc__banner__btn-accept");
    private By alertDlgBtnDecline = By.id("shopify-pc__banner__btn-decline");

    // Manage preferences
    private By prefsDlg = By.id("#shopify-pc__prefs__dialog");
    private By prefsDlgBtnClose = By.cssSelector("button#shopify-pc__prefs__header-close");//
    private By prefsDlgBtnDecline = By.cssSelector("button#shopify-pc__prefs__header-decline");//3
    private By prefsDlgBtnSave = By.cssSelector("button#shopify-pc__prefs__header-save");//1

    private By prefsDlgIntroH = By.cssSelector("div.shopify-pc__prefs__intro-main > h3");//7
    private By prefsDlgIntroP = By.cssSelector("div.shopify-pc__prefs__intro-main > p");//7

    private By prefsDlgOptionE = By.cssSelector("div#shopify-pc__prefs__essential");
    private By prefsDlgOptionP = By.cssSelector("div#shopify-pc__prefs__preferences");
    private By prefsDlgOptionM = By.cssSelector("div#shopify-pc__prefs__marketing");
    private By prefsDlgOptionA = By.cssSelector("div#shopify-pc__prefs__analytics");

    private By ageGateMonthSelect = By.cssSelector("select#age_gate_month");
    private By ageGateDayInput = By.cssSelector("input#age_gate_day");
    private By ageGateYearInput = By.cssSelector("input#age_gate_year");
    private By ageGateBtnProceed = By.xpath("//button[text()='Confirm age and proceed to BC Cannabis Stores']");
    private By ageGateErrMessage = By.xpath("//div[contains(text(), 'Shop access is limited')]");

    private By siteLogo = By.cssSelector(".site-logo > img");
    private By shoppingCartIcon = By.cssSelector("div.site-header-cart");

    public void inputAge(String month, int day, int year){
        //ToDo: error handling

        WebElement ddlEl = waitForElementToBeVisible(ageGateMonthSelect);
        Select ddl = new Select(ddlEl);
        ddl.selectByVisibleText(month);

        WebElement dayInputEl = waitForElementToBeVisible(ageGateDayInput);
        dayInputEl.sendKeys(Integer.toString(day));
        WebElement yearInputEl = waitForElementToBeVisible(ageGateYearInput);
        yearInputEl.sendKeys(Integer.toString(year));
    }

    public String getErrorMessageOfAgeGate(){
        WebElement errEl = waitForElementToBeVisible(ageGateErrMessage);
        return errEl.getText();
    }

    public void selectConfirmAgeProceedButton(){
        waitForElementToBeVisible(ageGateBtnProceed).click();
    }

    public String getLogoImageSource(){
        WebElement el = waitForElementToBeVisible(siteLogo);
        String actualLogoSrc = el.getAttribute("src");
        return actualLogoSrc;
    }

    public boolean isShoppingCartShowing(){
        try {
            waitForElementToBeVisible(shoppingCartIcon);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Map<String, String> setupMapDataOfElements(WebElement header, WebElement para){
        Map<String, String> txtMapData = new HashMap<>();
        String headerTxt = header.getText();
        String paraTxt = para.getText();
        System.out.println("headerTxt---" + headerTxt);
        System.out.println("paraTxt---" + paraTxt);
        txtMapData.put("headerTxt", headerTxt);
        txtMapData.put("paraTxt", paraTxt);
        return txtMapData;
    }

    public Map<String, String> getHeaderAndParaTextOfAlertDialog(){
        WebElement h = waitForElementToBeVisible(alertDlgH);
        WebElement p = waitForElementToBeVisible(alertDlgP);

        Map<String, String> txtMap = new HashMap<>();
        txtMap = setupMapDataOfElements(h, p);
        return txtMap;
    }

    public void selectManagePreferencesButton(){
        waitForElementToBeVisible(alertDlgBtnMgmPrefs).click();
    }

    public void selectDeclineButton(){
        waitForElementToBeVisible(alertDlgBtnDecline).click();
    }

    public Map<String, String> getHeaderAndParaTextOfPrefsIntro(){
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(prefsDlgIntroH));
        WebElement h = headers.get(0);
        List<WebElement> ps = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(prefsDlgIntroP));
        WebElement p = ps.get(0);

        Map<String, String> txtMap = new HashMap<>();
        txtMap = setupMapDataOfElements(h, p);
        return txtMap;
    }

    public Map<String, String> getHeaderAndParaTextOfPrefsOptionE(){
        List<WebElement> optionEs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(prefsDlgOptionE));
        WebElement option = optionEs.get(0);
        WebElement optionLbl = option.findElement(By.cssSelector(" label"));
        WebElement optionP = option.findElement(By.cssSelector(" p"));

        Map<String, String> txtMap = new HashMap<>();
        txtMap = setupMapDataOfElements(optionLbl, optionP);
        return txtMap;
    }

    public void selectPrefsOptionP(){
        waitForElementToBeClickable(prefsDlgOptionP).click();
    }

    public void selectPrefsOptionM(){
        waitForElementToBeClickable(prefsDlgOptionM).click();
    }

    public void selectPrefsOptionA(){
        waitForElementToBeClickable(prefsDlgOptionA).click();
    }

    public void selectSaveButton(){
        waitForElementToBeClickable(prefsDlgBtnSave).click();
    }

    public boolean isPrefsDialogStillShowing(){
        try {
            waitForElementToBeVisible(prefsDlg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAlertDialogStillShowing(){
        try {
            waitForElementToBeVisible(alertDlg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
