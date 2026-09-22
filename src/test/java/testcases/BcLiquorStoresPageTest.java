package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BcLiquorStoresPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BcLiquorStoresPageTest extends BaseTest {

    // 1. Declare the object at the class level
    private BcLiquorStoresPage liquorStoresPage;

    @BeforeMethod(alwaysRun = true)
    private void initializeBcLiquorStoresPage() {
        // 2. Instantiate it once before every test method runs
        // 'driver' comes from BaseTest and is guaranteed to be fresh here
        liquorStoresPage = new BcLiquorStoresPage(driver);
        //Quick step to go the page instead of launching from HomePage link
        driver.navigate().to("https://www.bcliquorstores.com/");
    }


    @Test(groups = {"Regression", "Retails"})
    public void testSearchingWithProductName(){
        String searchText = "Japanese plum";
        liquorStoresPage.enterSearchTerm(searchText);
        threadSleep(3);

        List<String> prodNamesOfSearchResult = liquorStoresPage.getAllProductDetails("names");
        boolean hasSearchText = false;
        for ( int i=0; i< prodNamesOfSearchResult.size(); i++ ){
            System.out.println( "prodNamesOfSearchResult.get(i)===" + prodNamesOfSearchResult.get(i));
            hasSearchText = prodNamesOfSearchResult.get(i).contains(searchText.toUpperCase());
            Assert.assertTrue(hasSearchText,
                    "Search result product[" + i +"] should contain the search text");
        }
    }

    @Test(groups = {"Regression", "Retails"})
    public void testSearchingWithSKU(){
        String searchText = "9035";//CHOYA 23 - UMESHU JAPANESE PLUM LIQUEUR
        liquorStoresPage.enterSearchTerm(searchText);
        threadSleep(3);

        liquorStoresPage.selectCertainProductInPage(1);
        threadSleep(3);
        String prodSKU = liquorStoresPage.getProductDetail("SKU");
        Assert.assertTrue(prodSKU.contains(searchText),
                "Search result product[" + prodSKU + "] should contain the search text");
    }

    @Test(groups = {"Regression", "Retails"})
    public void testSearchingWithCaseInsensitivity(){
        String searchText = "NAKANO";
        liquorStoresPage.enterSearchTerm(searchText);
        threadSleep(3);
        List<String> prodNames1 = new ArrayList<>(liquorStoresPage.getAllProductDetails("names"));

        searchText = "naKaNo";
        liquorStoresPage.enterSearchTerm(searchText);
        threadSleep(3);
        List<String> prodNames2 = new ArrayList<>(liquorStoresPage.getAllProductDetails("names"));

        // Sort both alphabetically
        Collections.sort(prodNames1);
        Collections.sort(prodNames2);
        // Compare the sorted lists
        Assert.assertEquals(prodNames1, prodNames2, "Search text results should be case insensitive");

        boolean hasSearchText = false;
        for ( int i=0; i< prodNames2.size(); i++ ){
            System.out.println( "prodNamesOfSearchResult.get(i)===" + prodNames2.get(i));
            hasSearchText = prodNames2.get(i).contains(searchText.toUpperCase());
            Assert.assertTrue(hasSearchText,
                    "Search result product[" + i +"] should contain the search text");
        }




    }

    @Test(groups = {"Regression", "Retails"})
    public void testSearchingWithSpecialCharacters(){
        String searchText = "F!h#j&k%";
        liquorStoresPage.enterSearchTerm(searchText);
        threadSleep(3);

        String errorMessage = liquorStoresPage.getSearchErrorMessage();
        Assert.assertTrue(errorMessage.contains("did not return any results"), "Search error message is wrong");
        String searchTipsText = liquorStoresPage.getSearchTips();
        Assert.assertTrue(searchTipsText.contains("Try these tips and search again"), "Search error message is wrong");
        Assert.assertTrue(searchTipsText.contains("Double check the spelling"), "Search error message is wrong");
        Assert.assertTrue(searchTipsText.contains("Avoid the use of common words such as 'the' or 'a' or single letters"), "Search error message is wrong");
        Assert.assertTrue(searchTipsText.contains("The product may be only available for wholesale distribution or is currently not available in our stores."), "Search error message is wrong");
    }

    @Test(groups = {"Regression"})
    public void testIcewinePageInfo() {
        liquorStoresPage.selectSubMenuFromMainMenu("Wine", "Icewine");
        String h3Title = liquorStoresPage.getH3Title();
        String icewineDescription = liquorStoresPage.getDescription();
        String expectedIcewineDescription = "Discover the unique sweetness of Icewine, " +
                "crafted from grapes naturally frozen on the vine. Rich, aromatic, and intensely flavourful, " +
                "Icewine pairs beautifully with desserts, cheese, and special celebrations. Explore top varieties, " +
                "tasting notes, and food pairings to find the perfect bottle and " +
                "learn what makes Icewine one of Canada’s most iconic wine styles.";

        Assert.assertEquals(h3Title, "ICEWINE",
                "Icewine H3 title is wrong");
        Assert.assertEquals(icewineDescription, expectedIcewineDescription,
                "Icewine description is wrong");
    }

    @Test(groups = {"Regression", "Retails"})
    public void testFilteringWithBCVQAandCheckIcon(){
        //Go to Icewine page
        liquorStoresPage.selectSubMenuFromMainMenu("Wine", "Icewine");
        liquorStoresPage.selectFilter("BC VQA");
        threadSleep(3);
        liquorStoresPage.selectCertainProductInPage(1);
        threadSleep(3);
        Assert.assertTrue(liquorStoresPage.isBCVQAiconDisplayed(),
                "The first product should have BC VQA icon displayed after BC VQA filtering");
        //Go back previous filter result page
        driver.navigate().back();
        threadSleep(5);
        liquorStoresPage.selectCertainProductInPage(8);
        Assert.assertTrue(liquorStoresPage.isBCVQAiconDisplayed(),
                "The eighth product should have BC VQA icon displayed after BC VQA filtering");
    }

    @Test(groups = {"Regression", "Retails"})
    public void testFilteringWithVolumeAndCheckVolume(){
        //Go to Icewine page
        liquorStoresPage.selectSubMenuFromMainMenu("Wine", "Icewine");
        //Select 0mL-249mL
        liquorStoresPage.selectFilter("VOLUME");
        threadSleep(3);

        List<String> prodVolumes = liquorStoresPage.getAllProductDetails("volumes");
        boolean hasVolumeText = false;
        for ( int i=0; i< prodVolumes.size(); i++ ){
            System.out.println( "prodVolumes.get(i)===" + prodVolumes.get(i));
            //ToDo: up to < 249ml
            hasVolumeText = prodVolumes.get(i).contains("200 ml");
            Assert.assertTrue(hasVolumeText,
                    "product[" + i +"] should contain the volume text");
        }

//        liquorStoresPage.selectCertainProductInPage(1);
//        threadSleep(3);
//        Assert.assertEquals(liquorStoresPage.getProductDetail("volume"), "200 ml",
//                "The product should have 200 ml volume after volume filtering");
    }

    //Helper method to strip out currency symbols/commas and convert to Double
    private static double parsePrice(String priceText) {
        // Regex [^\d.] removes everything except digits and decimal points
        String cleanPrice = priceText.replaceAll("[^\\d.]", "");
        return Double.parseDouble(cleanPrice);
    }

    @Test(groups = {"Regression", "Retails"})
    public void testSortingWithPriceAndCheckArrow(){
        //Go to Icewine page
        liquorStoresPage.selectSubMenuFromMainMenu("Wine", "Icewine");
        //First time sorting
        liquorStoresPage.selectSorting("price");
        threadSleep(3);
        Assert.assertFalse(liquorStoresPage.isPriceSortingArrowUp(),
                "The price arrow should be down after sorting once");
        liquorStoresPage.selectCertainProductInPage(1);
        threadSleep(3);
        String firstProdText = liquorStoresPage.getProductDetail("price");
        System.out.println("First product is===" + firstProdText);
        //Go back previous filter result page
        driver.navigate().back();
        threadSleep(3);
        liquorStoresPage.selectCertainProductInPage(8);
        threadSleep(3);
        String eighthProdText = liquorStoresPage.getProductDetail("price");
        System.out.println("Eighth product is===" + eighthProdText);
        //Go back previous filter result page
        driver.navigate().back();
        threadSleep(3);

        double firstProdPrice = parsePrice(firstProdText);
        double eighthProdPrice = parsePrice(eighthProdText);
        double priceDifference = firstProdPrice - eighthProdPrice;
        Assert.assertTrue(priceDifference >=0,
                "Product price sorting is wrong");

        liquorStoresPage.selectSorting("price");
        threadSleep(3);
        Assert.assertTrue(liquorStoresPage.isPriceSortingArrowUp(),
                "The price arrow should be up after sorting twice");
    }

    @Test(groups = {"Regression", "Retails"})
    public void testSortingWithNameAndCheckAlphabet(){
        //Go to Icewine page
        liquorStoresPage.selectSubMenuFromMainMenu("Wine", "Icewine");

        //default: products alphabet up
        Assert.assertTrue(liquorStoresPage.isNameSortingAlphabetUp(),
                "The products should be sorted alphabetical up by default");

        liquorStoresPage.selectCertainProductInPage(1);
        threadSleep(3);
        String firstProdText = liquorStoresPage.getProductDetail("name");
        System.out.println("First product===" + firstProdText);
        //Go back previous filter result page
        driver.navigate().back();
        threadSleep(3);
        liquorStoresPage.selectCertainProductInPage(8);
        threadSleep(3);
        String eighthProdText = liquorStoresPage.getProductDetail("name");
        System.out.println("Eighth product is===" + eighthProdText);
        //Go back previous filter result page
        driver.navigate().back();
        threadSleep(3);

        boolean isSortedUp = firstProdText.compareTo(eighthProdText) <= 0;
        Assert.assertTrue(isSortedUp,
                "Product name sorting is wrong");

        liquorStoresPage.selectSorting("alphabet");//change to Z-A
        threadSleep(3);
        Assert.assertFalse(liquorStoresPage.isNameSortingAlphabetUp(),
                "The products should be sorted alphabetical down now");
    }

}
