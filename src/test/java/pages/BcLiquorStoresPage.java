package pages;

import net.bytebuddy.description.modifier.Visibility;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BcLiquorStoresPage extends BasePage {

    // Constructor accepts driver passed from the test script
    public BcLiquorStoresPage(WebDriver driver) {
        super(driver); // Chains the driver instance up to BasePage
    }

    private By menuBar = By.id("superfish-main");
    private By SpiritsLnk = new ByChained(menuBar, By.xpath("//a[text()='Spirits']"));
    private By WineLnk = new ByChained(menuBar, By.xpath("(//a[text()='Wine'])[1]"));

    private By searchInput = By.cssSelector("input#header-search");
    private By noResultsMessage = By.className("no-results");
    private By searchTips = By.cssSelector(".no-results .search_tips");



    private By h3Title = By.cssSelector("div.description-container h3.title");
    private By description = By.cssSelector("div.description-container p.description");

    //many products on page
    private By productImageContainers = By.className("product-image-container");
    private By productNames = By.cssSelector(".product-description-container .product-name");
    private By productVolumes = By.cssSelector(".product-description-container .product-subtext span");
    private By productPrices = By.cssSelector(".product-description-container .product-price");

    //Filter
    private By bcVQAfilter = By.xpath("//label[text()='BC VQA']");
    private By priceFilter = By.xpath("//h3[text()='Price']");
    private By lowPriceFilter = By.xpath("//label[text()=\"$20.0 - $49.99\"]");
    private By volumeFilter = By.xpath("//h3[text()='Volume']");
    private By lowVolumeFilter = By.xpath("//label[text()='0 mL  - 249 mL']");

    //Sorting
    private By alphabetSorting = By.cssSelector(".alphabeticalProducts");
    private By alphabetOrder = By.cssSelector(".alphabeticalProducts a");
    private By priceSorting = By.cssSelector("li.currentPrice");
    private By priceArrow = By.cssSelector("i.fa");

    //Product details page
    private By productName = By.cssSelector("h1");
    private By bcVQAicon = By.cssSelector("a.icon-bc-vqa");
    private By volumeLabel = By.xpath("(//span[contains(@data-bind, 'volume')])[1]");
    private By priceLabel = By.xpath("(//div[contains(@class, 'product-price')])[1]");
    private By sKULabel = By.xpath("//span[contains(@data-bind, 'SKU:')]");

    //Main page
    public void selectSubMenuFromMainMenu(String mainMenuText, String subMenuText){
        WebElement ddl = null;
        if ( mainMenuText == "Spirits"){
            ddl = waitForElementToBeClickable(SpiritsLnk);
        } else if ( mainMenuText == "Wine"){
            ddl = waitForElementToBeClickable(WineLnk);
        }
        //Element should have been "select" but was "a", so cannot use Select
        //Select dropdown = new Select(ddl);
        //dropdown.selectByVisibleText(subMenuText);

        //directly click main menu will launch main menu page, instead of display ddl and then go submenu
        Actions actions = new Actions(driver);
        actions.moveToElement(ddl).perform();//hover
        WebElement menuItem = waitForElementToBeVisible(By.xpath("//a[text()='" + subMenuText + "']"));
        menuItem.click();
    }

    public void enterSearchTerm(String searchTerm){
        waitForElementToBeVisible(searchInput).sendKeys(searchTerm, Keys.ENTER);
    }

    public String getSearchErrorMessage(){
        return waitForElementToBeVisible(noResultsMessage).getText();
    }

    public String getSearchTips(){
        return waitForElementToBeVisible(searchTips).getText();
    }

    public String getH3Title(){
        return waitForElementToBeVisible(h3Title).getText();
    }

    public String getDescription(){
        return waitForElementToBeVisible(description).getText();
    }

    public List<String> getAllProductDetails(String productDetails){
        By elBy = null;
        switch (productDetails) {
            case "names":
                elBy = productNames;
                break; // Exits the switch block
            case "volumes":
                elBy = productVolumes;
                break;
            case "prices":
                elBy = productPrices;
                break;
            default:
                elBy = productNames;
                break;
        }
        List<WebElement> els = driver.findElements(elBy);
        List<String> prodNames = new ArrayList<>();
        for (WebElement el : els) {
            System.out.println("el.getText()===" + el.getText());
            prodNames.add(el.getText());
        }
        return prodNames;
    }


    //Wine type page
    //ToDo: add rating into sorting?
    public void selectSorting (String sortingText){
        //Move to page upper part to show the sorting menu options
        javaScriptExecutorScrollToViewTrue(driver.findElement(h3Title));
        if ( sortingText == "alphabet"){
            waitForElementToBeClickable(alphabetSorting).click();
        } else if ( sortingText == "price"){
            waitForElementToBeClickable(priceSorting).click();
        }
    }

    //Only check the sorting menu label display
    public boolean isPriceSortingArrowUp(){
        waitForElementToBeVisible(priceSorting);
        WebElement priceArrowEl = driver.findElement(priceSorting).findElement(priceArrow);
        //class="fa fa-long-arrow-up" or "fa fa-long-arrow-down"
        String attributeValue = priceArrowEl.getAttribute("class").toLowerCase();

        boolean arrowUp = Boolean.parseBoolean(null);
        if  (attributeValue.contains("up")){
            arrowUp = true;
        }  else if (attributeValue.contains("down")){
            arrowUp = false;
        };
        return arrowUp;
    }

    //Only check the sorting menu label display
    public boolean isNameSortingAlphabetUp(){
        waitForElementToBeVisible(alphabetSorting);
        WebElement productsAlphabet = waitForElementToBeVisible(alphabetOrder);
        //class="fa fa-long-arrow-up" or "fa fa-long-arrow-down"
        String attributeValue = productsAlphabet.getText();
        System.out.println("productsAlphabeticalOrder===" +  attributeValue);

        boolean alphabetUp = Boolean.parseBoolean(null);
        if  (attributeValue.equals("A - Z")){
            alphabetUp = true;
        }  else if (attributeValue.equals("Z - A")){
            alphabetUp = false;
        };
        return alphabetUp;
    }

    //ToDo: add 2 levels of filters?
    public void selectFilter(String filterText){
        if ( filterText == "BC VQA"){
            waitForElementToBeClickable(bcVQAfilter).click();
        } else if ( filterText == "PRICE"){
            //To display options
            waitForElementToBeClickable(priceFilter).click();
            //0mL - 249mL
            waitForElementToBeClickable(lowPriceFilter).click();
        } else if (filterText == "VOLUME"){
            waitForElementToBeClickable(volumeFilter).click();
            waitForElementToBeClickable(lowVolumeFilter).click();
        }
    }

    public void selectCertainProductInPage(int productIndexInPage){
        List<WebElement> els = wait.until(ExpectedConditions.
                visibilityOfAllElements(driver.findElements(productImageContainers)));
        if (productIndexInPage > els.size()){
            throw new IllegalArgumentException("wrong index of the products in page");
        } else {
            els.get(productIndexInPage-1).click();//the 1st element
        }
    }

    //Product details page actions
    public boolean isBCVQAiconDisplayed(){
        WebElement el = waitForElementToBeVisible(bcVQAicon);
        return el.isDisplayed();
    }

    public String getProductDetail(String productDetail) {
        By elBy = null;
        switch (productDetail) {
            case "name":
                elBy = productName;
                break; // Exits the switch block
            case "volume":
                elBy = volumeLabel;
                break;
            case "price":
                elBy = priceLabel;
                break;
            case "SKU":
                elBy = sKULabel;
                break;
            default:
                elBy = productName;
                break;
        }
        WebElement el = waitForElementToBeVisible(elBy);
        return el.getText();
    }

}
