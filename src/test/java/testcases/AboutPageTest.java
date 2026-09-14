package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AboutPage;
import pages.HomePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutPageTest extends BaseTest {

    // 1. Declare the object at the class level
    private AboutPage aboutPage;

    @BeforeMethod(alwaysRun = true)
    private void initializeAboutPage() {
        // 2. Instantiate it once before every test method runs
        // 'driver' comes from BaseTest and is guaranteed to be fresh here
        aboutPage = new AboutPage(driver);
        //homePage = new HomePage(driver);
        //Quick step to go the page instead of launching from HomePage link
        driver.navigate().to("https://www.bcldb.com/about/about-ldb");
    }


    @Test(groups = {"Regression", "iFrameTest"})
    public void testCannabisVideoInfo() {
        // Declare a map (Key: String, Value: String)
        Map<String, String> videoResult = new HashMap<>();

        aboutPage.moveToVideoIFrame(aboutPage.cannabisVideoIFrame);
        videoResult = aboutPage.getSrcTitleOfVideoIFrame(aboutPage.cannabisVideoIFrame);

        Assert.assertEquals(videoResult.get("videoSrc"), "https://storetour.bcldb.com/",
                "Cannabis video source is wrong");
        Assert.assertEquals(videoResult.get("videoTitle"), "Cannabis Store Tour",
                "Cannabis video title is wrong");
    }

    @Test(groups = {"Regression", "iFrameTest"})
    public void testPopupBoxOfCannabisVideo() {
        // Declare a map (Key: String, Value: String)
        Map<String, String> popupBoxTextsResult = new HashMap<>();

        aboutPage.moveToVideoIFrame(aboutPage.cannabisVideoIFrame);
        popupBoxTextsResult = aboutPage.getPopupBoxTextsOfCannabisVideo();

        Assert.assertEquals(popupBoxTextsResult.get("titleTxt"), "AGE VERIFICATION",
                "Popup box title is wrong");
        Assert.assertEquals(popupBoxTextsResult.get("descriptionTxt"), "You must be 19 years of age or older to watch this video.",
                "Popup box description is wrong");
        Assert.assertEquals(popupBoxTextsResult.get("checkBoxTxt"), "I confirm that I am 19 years old or older.",
                "Popup box checkbox text is wrong");
        Assert.assertEquals(popupBoxTextsResult.get("proceedBtnTxt"), "Confirm & Proceed",
                "Popup box  button text is wrong");
    }

    @Test(groups = {"Regression", "iFrameTest"})
    public void testConfirmAgeVerificationToPlayCannabisVideo(){
        // Declare a map (Key: String, Value: String)
        Map<String, Double> timeStampsResult = new HashMap<>();

        aboutPage.moveToVideoIFrame(aboutPage.cannabisVideoIFrame);
        aboutPage.confirmProceedCannabisVideo();
        timeStampsResult = aboutPage.getTimeStampsOfVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);
        double secondTimestamp = (double) timeStampsResult.get("secondTimestamp");
        double firstTimestamp = (double) timeStampsResult.get("firstTimestamp");
        System.out.println("secondTimestamp: " + secondTimestamp);
        boolean isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);

        Assert.assertTrue(secondTimestamp > firstTimestamp,
                "Video time stamps should increase when playing");
        Assert.assertFalse(isPaused, "video should not be paused currently");
    }

    @Test(groups = {"Regression", "iFrameTest"})
    public void testPausePlayCannabisVideo(){
        boolean isPaused;
        aboutPage.moveToVideoIFrame(aboutPage.cannabisVideoIFrame);
        threadSleep(8);
        aboutPage.confirmProceedCannabisVideo();//play
        threadSleep(7);
        isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);
        Assert.assertFalse(isPaused, "Cannabis video should be playing currently");
        aboutPage.playPauseVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);//pause
        threadSleep(6);
        isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);
        Assert.assertTrue(isPaused, "Cannabis video should be paused currently");
        aboutPage.playPauseVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);//play
        threadSleep(5);
        isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.cannabisVideoIFrame, aboutPage.cannabisVideo);
        Assert.assertFalse(isPaused, "Cannabis video should be playing currently");
    }


    @Test(groups = {"Regression"})
    public void testMultiTheGoodPage(){
        aboutPage.selectMultiplyTheGoodLink();
        String actualUrl = driver.getCurrentUrl();
        String actualPageTitle = driver.getTitle();
        Assert.assertTrue(actualUrl.contains("/about/multiply-the-good"),
                "Multiply the Good Page is not launched");
        Assert.assertEquals(actualPageTitle, "Multiply the Good | BCLDB Corporate",
                "Multiply the Good Page title is wrong");
        threadSleep(10);
    }

    @Test(groups = {"Regression", "iFrameTest"})
    public void testYouTubeVideoInfo() {
        // Declare a map (Key: String, Value: String)
        Map<String, String> videoResult = new HashMap<>();

        aboutPage.selectMultiplyTheGoodLink();
        aboutPage.moveToVideoIFrame(aboutPage.youtubeVideoIFrame);
        videoResult = aboutPage.getSrcTitleOfVideoIFrame(aboutPage.youtubeVideoIFrame);

        Assert.assertEquals(videoResult.get("videoSrc"),
                "https://www.youtube.com/embed/z1sqdBpo-qw?si=G3izl3NU4jkF1qjT&rel=0",
                "YouTube video source is wrong");
        Assert.assertEquals(videoResult.get("videoTitle"), "YouTube video player",
                "YouTube video title is wrong");
    }

    @Test(groups = {"Regression", "iFrameTest"})
    public void testPlayPauseYouTubeVideo(){
        boolean isPaused;
        aboutPage.selectMultiplyTheGoodLink();
        aboutPage.moveToVideoIFrame(aboutPage.youtubeVideoIFrame);
        threadSleep(8);
        aboutPage.playPauseVideo(aboutPage.youtubeVideoIFrame, aboutPage.youtubeVideoDiv);//play=========================
        threadSleep(7);
        isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.youtubeVideoIFrame, aboutPage.youtubeVideo);
        Assert.assertFalse(isPaused, "YouTube video should be playing currently");
        aboutPage.playPauseVideo(aboutPage.youtubeVideoIFrame, aboutPage.youtubeVideoDiv);//pause===========================
        threadSleep(6);
        isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.youtubeVideoIFrame, aboutPage.youtubeVideo);
        Assert.assertTrue(isPaused, "YouTube video should be paused currently");
        aboutPage.playPauseVideo(aboutPage.youtubeVideoIFrame, aboutPage.youtubeVideoDiv);//play==================================
        threadSleep(5);
        isPaused = aboutPage.getPlayStatusOfVideo(aboutPage.youtubeVideoIFrame, aboutPage.youtubeVideo);
        Assert.assertFalse(isPaused, "YouTube video should be playing again currently");
    }


}
