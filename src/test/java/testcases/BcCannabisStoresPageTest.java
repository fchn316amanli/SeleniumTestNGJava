package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import pages.BcCannabisStoresPage;

import java.util.HashMap;
import java.util.Map;



public class BcCannabisStoresPageTest extends BaseTest {

    // 1. Declare the object at the class level
    private BcCannabisStoresPage cannabisStoresPage;

    @BeforeMethod(alwaysRun = true)
    private void initializeBcCannabisStoresPage() {
        // 2. Instantiate it once before every test method runs
        // 'driver' comes from BaseTest and is guaranteed to be fresh here
        cannabisStoresPage = new BcCannabisStoresPage(driver);
        //Quick step to go the page instead of launching from HomePage footerLink
        driver.navigate().to("https://www.bccannabisstores.com/");
    }


    @Test(groups = {"Regression", "AlertDialogTest"})
    public void testAlertDialogTextAndDecline() {
        Map<String, String> dialogTextsResult = new HashMap<>();
        dialogTextsResult = cannabisStoresPage.getHeaderAndParaTextOfAlertDialog();

        Assert.assertEquals(dialogTextsResult.get("headerTxt"), "We value your privacy",
                "Alert Dialog title is wrong");
        Assert.assertEquals(dialogTextsResult.get("paraTxt"),
                "We use cookies and other technologies to personalize your experience, perform marketing, and collect analytics. Learn more in our Privacy Policy.",
                "Alert Dialog paragraph is wrong");
        threadSleep(3);
        cannabisStoresPage.selectDeclineButton();
        threadSleep(3);

        Assert.assertFalse(cannabisStoresPage.isAlertDialogStillShowing(),
                "Alert Dialog should not be displaying any more");
    }

    @Test(groups = {"Regression", "AlertDialogTest"})
    public void testPreferencesManagementText(){
        cannabisStoresPage.selectManagePreferencesButton();
        Map<String, String> prefsTextsResult = new HashMap<>();
        prefsTextsResult = cannabisStoresPage.getHeaderAndParaTextOfPrefsIntro();

        Assert.assertEquals(prefsTextsResult.get("headerTxt"), "You control your data",
                "Preference Management title is wrong");
        Assert.assertEquals(prefsTextsResult.get("paraTxt"),
                "Learn more about the cookies we use, and choose which cookies to allow.",
                "Preference Management paragraph is wrong");
    }

    @Test(groups = {"Regression", "AlertDialogTest"})
    public void testPreferencesOptionEssentialsText(){
        cannabisStoresPage.selectManagePreferencesButton();
        Map<String, String> prefsTextsResult = new HashMap<>();
        prefsTextsResult = cannabisStoresPage.getHeaderAndParaTextOfPrefsOptionE();

        Assert.assertEquals(prefsTextsResult.get("headerTxt"), "Required",
                "Preference Management title is wrong");
        Assert.assertEquals(prefsTextsResult.get("paraTxt"),
                "These cookies are necessary for the site to function properly, including capabilities like logging in and adding items to the cart.",
                "Preference Management Option paragraph is wrong");
    }

    @Test(groups = {"Regression", "AlertDialogTest"})
    public void testSelectPreferencesOptionsAndSave(){
        cannabisStoresPage.selectManagePreferencesButton();
        cannabisStoresPage.selectPrefsOptionA();
        cannabisStoresPage.selectPrefsOptionM();
        cannabisStoresPage.selectPrefsOptionP();
        threadSleep(3);
        cannabisStoresPage.selectSaveButton();
        threadSleep(3);

        Assert.assertFalse(cannabisStoresPage.isPrefsDialogStillShowing(),
                "Prefs Dialog should not be displaying any more");
        Assert.assertFalse(cannabisStoresPage.isAlertDialogStillShowing(),
                "Alert Dialog should not be displaying any more");
    }

    @Test(groups = {"Regression", "ErrorMessagesTest"}, dataProvider = "ageData")
    public void testInputYoungAgeError(String month, int day,int year){
        //Dismiss the alert dialog to proceed the main page
        cannabisStoresPage.selectDeclineButton();
        // Input a young age
        cannabisStoresPage.inputAge(month, day, year);
        threadSleep(5);
        String errorMessageAgeGate = cannabisStoresPage.getErrorMessageOfAgeGate();
        Assert.assertTrue(errorMessageAgeGate.contains(
                "Shop access is limited to adults of legal age. Proceed unverified to view unrestricted content."),
                "Age gate error message is wrong");
        Assert.assertTrue(errorMessageAgeGate.contains(
                        "More about age restrictions\n"),
                "Age gate error message is wrong");
        Assert.assertTrue(errorMessageAgeGate.contains(
                        "Proceed unverified"),
                "Age gate error message is wrong");
    }

    //return two-dimensional Object array (Object[][]).
    // Each row in this array represents a separate test execution,
    // and each element corresponds to a parameter in the test method.
    @DataProvider(name = "ageData")
    public Object[][] getAgeData(Method method) {
        // Dynamic Data Selection (Method Runtime Check)
        // Manipulate returned data conditionally based on which method calls it
        if (method.getName().equalsIgnoreCase("testInputLegalAgeProceed")) {
            return new Object[][]{//>=19 years old
                    {"January", 23, 1928},//old age
                    {"January", 1, 1985},//middle
                    {"September", 16, 2007}//edge case, young
            };
        } else {
            return new Object[][] { {"October", 13, 2010} };
        }
    }

    //Happy path with different adult ages
    @Test(groups = {"Regression"}, dataProvider = "ageData")
    public void testInputLegalAgeProceed(String month, int day,int year){
        //Dismiss the alert dialog to proceed the main page
        cannabisStoresPage.selectDeclineButton();
        // Input an age
        cannabisStoresPage.inputAge(month, day, year);
        threadSleep(5);
        cannabisStoresPage.selectConfirmAgeProceedButton();
        threadSleep(5);
        // Verify BC Cannabis Stores page is launched
        String logoSrc = cannabisStoresPage.getLogoImageSource();
        Assert.assertTrue(logoSrc.contains("BCCannabisStores-Logo-White"),
                "The logo image source URL is wrong!");
        Assert.assertTrue(cannabisStoresPage.isShoppingCartShowing(),
                "Shopping cart is NOT showing");
    }

}
