package testcases;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import pages.BcCannabisStoresPage;
import pages.BcLiquorStoresPage;
import pages.HomePage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HomePageTest extends BaseTest {

    // 1. Declare the object at the class level
    private HomePage homePage;
    private List<String> slidesTitles = new ArrayList<>();
    private String slideTitle1 = null;
    private String slideTitle2 = null;

    @BeforeMethod(alwaysRun = true)
    private void initializeHomePage() {
        // 2. Instantiate it once before every test method runs
        // 'driver' comes from BaseTest and is guaranteed to be fresh here
        homePage = new HomePage(driver);
        driver.navigate().to("https://www.bcldb.com/");
    }


    @Test (groups = {"Smoke"})
    public void testHomePage() {
        String actualLogoSource = homePage.getLogoImageSource();
        String actualPageTitle = driver.getTitle();
        Assert.assertTrue(actualLogoSource.contains("bcldb_logo.png"),
                "The logo image source URL does not contain logo.png!");
        Assert.assertEquals(actualPageTitle, "home | BCLDB Corporate",
                "HomePage title is wrong");
        threadSleep(10);
    }

    @Test(groups = {"Smoke"})
    public void testLaunchAboutPageThroughLink() {
        homePage.selectAboutLink();
        String actualUrl = driver.getCurrentUrl();
        String actualPageTitle = driver.getTitle();
        Assert.assertTrue(actualUrl.contains("about/about-ldb"),
                "About Page is not launched");
        Assert.assertEquals(actualPageTitle, "About the LDB | BCLDB Corporate",
                "About Page title is wrong");
        threadSleep(10);
    }

    //ToDo: may remove
    @Test (groups = {"Smoke"})
    public void testLaunchBizLiquorPageThroughLink() {
        homePage.selectDoingBizLink();
        String actualUrl = driver.getCurrentUrl();
        String actualPageTitle = driver.getTitle();
        Assert.assertTrue(actualUrl.contains("doing-business-ldb"),
                "Doing Business Page is not launched");
        Assert.assertEquals(actualPageTitle, "Doing Business With LDB | BCLDB Corporate",
                "Doing Business Page title is wrong");
        threadSleep(10);
    }

    //ToDo: may remove
    @Test (groups = {"Smoke"})//test case needed?
    public void testLaunchBizCannabisPageThroughLink() {
        homePage.selectDoingBizCannabisLink();
        String actualUrl = driver.getCurrentUrl();
        String actualPageTitle = driver.getTitle();
        Assert.assertTrue(actualUrl.contains("bcldb-cannabis-supplier-information"),
                "Doing Business Cannabis Page is not launched");
        Assert.assertEquals(actualPageTitle, "Supplier Information - Central Delivery | BCLDB Corporate",
                "Doing Business Cannabis Page title is wrong");
        threadSleep(10);
    }

    @Test (groups = {"Smoke"})
    public void testSwiperSlidesVsBulletImages() {
        // 1. verify swiper slides count equals to swiper bullet images count
        Assert.assertEquals(homePage.getSwiperSlidesCount(),
                homePage.getSwiperBulletImgsCount(),
                "Swiper slides should match Swiper Bullet Images");
    }

    @Test (groups = {"Regression", "SwiperSlidesTest"})
    public void testSwiperBulletImagesToLoop() {
        slideTitle1 = null;
        slideTitle2 = null;
        slidesTitles.clear();

        // 2. Select swiper bullet images to loop all slides
        for (int i = homePage.getSwiperBulletImgsCount()-1; i >= 0; i--) {
            homePage.clickSwiperBulletImg(i);
            threadSleep(1);//wait the slide transition
            slideTitle1 = homePage.getActiveSlideCaptionTitle();
            slidesTitles.add(slideTitle1);
            Assert.assertNotEquals(slideTitle1, slideTitle2,
                    "Bullet Img button should change the slides");
            System.out.println("slideTitle1--" + slideTitle1);
            System.out.println("slideTitle2--" + slideTitle2);
            slideTitle2 = slideTitle1;
        }
        //Verify all slide caption titles in loop are different
        Assert.assertEquals(slidesTitles.size(), slidesTitles.stream().distinct().count(),
                "Slide caption titles should be different");

        // 3. verify there is only one bullet image is active? highlighted?
        Assert.assertEquals(homePage.getAriaCurrentTrueCountOfSwiperBulletImgs(), 1,
                "There should be only one bullet image having aria-current=true");
    }

    @Test (groups = {"Regression", "SwiperSlidesTest"})
    public void testSwiperPlayPauseButton() {
        slideTitle1 = null;
        slideTitle2 = null;
        // 4. Verify pause and play buttons
        homePage.clickSwiperPlayPauseButton();//Pause
        slideTitle1 = homePage.getActiveSlideCaptionTitle();
        threadSleep(10);
        slideTitle2 = homePage.getActiveSlideCaptionTitle();
        Assert.assertEquals(slideTitle1, slideTitle2,
                "Swiper Pause button should pause slide moving to the next");
        System.out.println("slideTitle1--" + slideTitle1);
        System.out.println("slideTitle2--" + slideTitle2);

        homePage.clickSwiperPlayPauseButton();//play
        threadSleep(7);
        slideTitle2 = homePage.getActiveSlideCaptionTitle();
        Assert.assertNotEquals(slideTitle1, slideTitle2,
                "Swiper Play button should let slide moving to the next");
        System.out.println("slideTitle1--" + slideTitle1);
        System.out.println("slideTitle2--" + slideTitle2);
    }

    private void SelectNextOrPrevBtnToLoop(String nextOrPrev) {
        //reset list, in case conflict in between test cases
        slideTitle1 = null;
        slideTitle2 = null;
        slidesTitles.clear();

        // 5. Select swiper Next button to loop all slides
        for (int i = 0; i < homePage.getSwiperSlidesCount(); i++) {
            homePage.clickSwiperNextOrPrevButton(nextOrPrev);
            System.out.println("next!");
            threadSleep(1);//wait the slide transition
            slideTitle1 = homePage.getActiveSlideCaptionTitle();
            slidesTitles.add(slideTitle1);
            Assert.assertNotEquals(slideTitle1, slideTitle2,
                    "Swiper Next/Prev button should change the slides");
            System.out.println("slideTitle1--" + slideTitle1);
            System.out.println("slideTitle2--" + slideTitle2);
            slideTitle2 = slideTitle1;
        }
        //Verify all slide caption titles in loop are different
        Assert.assertEquals(slidesTitles.size(), slidesTitles.stream().distinct().count(),
                "Slide caption titles should be different");
    }

    @Test (groups = {"Regression", "SwiperSlidesTest"})
    public void testSwiperNextButtonToLoop() {
        SelectNextOrPrevBtnToLoop("Next");
    }

    @Test (groups = {"Regression", "SwiperSlidesTest"})
    public void testSwiperPrevButtonToLoop() {
        SelectNextOrPrevBtnToLoop("Previous");
    }

    @Test (groups = {"Regression"})
    public void testUnderBannerLabel() {
        String actualUnderBannerText = homePage.getUnderBannerText();
        String expectedUnderBannerText = "QUICK LINKS TO DOING BUSINESS WITH THE BC LIQUOR DISTRIBUTION BRANCH";
        Assert.assertEquals(actualUnderBannerText, expectedUnderBannerText, "Under Banner label is wrong");
    }

    @Test (groups = {"Smoke"})
    public void testRetailsViewLinkAppearance() {
        //Verify the View Link image
        String actualRetailsViewImgSource = homePage.getRetailsViewLinkImageSource();
        Assert.assertTrue(actualRetailsViewImgSource.contains("bc-liquor-stores.jpg"),
                "The Retails View link image source URL does not contain store.jpg");

        String actualRetailsViewTextHeader = homePage.getRetailsViewLinkTextHeader();
        Assert.assertEquals(actualRetailsViewTextHeader, "BC LIQUOR STORES »",
                "The Retails View link text header is wrong");

        String actualRetailsViewText = homePage.getRetailsViewLinkText();
        String expectedRetailsViewText = "Learn about Products, Wine Pairing, Store Locations and In-Store Programs.";
        Assert.assertEquals(actualRetailsViewText, expectedRetailsViewText,
                "The Retails View link text is wrong");
    }

    @Test (groups = {"Smoke"})
    public void testLaunchRetailsPageThroughRetailsViewLink() {
        homePage.selectRetailsViewLink();
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("retail-customers"),
                "Retail Customers Page is not launched");
        String actualPageTitle = driver.getTitle();
        Assert.assertEquals(actualPageTitle, "Retail Customers | BCLDB Corporate",
                "Retail Customers Page title is wrong");
        threadSleep(10);
    }

    @Test (groups = {"Smoke"})//launch a new tab of page
    public void testLaunchBcLiquorStoresPageThroughFooterLink() {
        // 1. Store the original parent window handle, to switch back later
        System.out.println("Old page WindowHandle: " + driver.getWindowHandle());
        // 2. Perform the action that opens the new tab
        BcLiquorStoresPage bcLiquorStoresPage = homePage.selectBcLiquorStoresFooterLink();
        // 3. Get all open window handles into a list or set
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        // 4. Switch to the newly opened tab (index 1 is the second tab)
        threadSleep(4);
        driver.switchTo().window(tabs.get(1));
        threadSleep(3);
        // Now the driver is executing actions on the new tab
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("bcliquorstores.com"),
                "BCLIQUOR Stores Page is not launched");
        // when a new page launched, page windowHandle is changed
        System.out.println("New page WindowHandle: " + driver.getWindowHandle());
        String actualPageTitle = driver.getTitle();
        Assert.assertEquals(actualPageTitle, "BCLIQUOR: Great Deals on Wine, Beer & Spirits",
                "BCLIQUOR Stores Page title is wrong");
    }

    @Test (groups = {"Smoke"})//overlap the original page
    public void testLaunchBcCannabisStoresPageThroughFooterLink() {
        System.out.println("Old page WindowHandle: " + driver.getWindowHandle());
        BcCannabisStoresPage bcCannabisStoresPage = homePage.selectBcCannabisStoresFooterLink();
        threadSleep(3);
        // Now the driver is executing actions on the new page
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("bccannabisstores.com"),
                "BC Cannabis Stores Page is not launched");
        // when page overlapping, page windowHandle is NOT changed
        System.out.println("New page WindowHandle: " + driver.getWindowHandle());
        String actualPageTitle = driver.getTitle();
        Assert.assertEquals(actualPageTitle, "BC Cannabis Stores",
                "BC Cannabis Stores Page title is wrong");
    }

}
