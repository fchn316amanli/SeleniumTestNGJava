package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

        private WebDriver driver;
        private WebDriverWait wait;

        // Constructor accepts driver passed from the test classes
        // Initialize driver and wait in the constructor
        public HomePage(WebDriver driver) {
                this.driver = driver;
                this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        }

        // Keep locators private to prevent Test Class exposure
        private By logoImg = By.cssSelector("div#block-bcldb-sitebranding > a > img");
        private By PrimaryLnks = By.cssSelector("nav#block-bcldb-primarylinks");//mutli
        private By AboutLnk = new ByChained(PrimaryLnks, By.xpath("//a[text()='About']"));
        private By CareersLnk = new ByChained(PrimaryLnks, By.xpath("//a[text()='Careers']"));
        private By BizLiquorLnk = new ByChained(PrimaryLnks, By.xpath("//a[text()='Doing Business - LDB LIQUOR']"));
        private By BizCannabisLnk = new ByChained(PrimaryLnks, By.xpath("//a[text()='DOING BUSINESS - LDB CANNABIS']"));


        private By swiper = By.className("bcldb-homepage-swiper");
        private By swiperSlides = By.className("swiper-slide");//mutli
        private By swiperPlayPauseBtn = By.className("bcldb-banner-btn--playpause");
        private By swiperPrevBtn = By.className("bcldb-banner-btn--prev");
        private By swiperNextBtn = By.className("bcldb-banner-btn--next");
        private By swiperBulletImgs = By.className("swiper-pagination-bullet"); //total=4, should -1

        private By activeSlide = By.cssSelector(".swiper-slide-active");
        private By slideMedia = By.className("bcldb-banner-slide__media"); //3
        private By slideCaption = By.className("bcldb-banner-slide__caption"); //3
        private By slideCaptionTitle = By.className("bcldb-banner-slide__title"); //3
        private By slideCaptionCta = By.className("bcldb-banner-slide__cta"); //3


        //"Quick links to doing business with the BC Liquor Distribution Branch"
        private By underBannerLbl = By.cssSelector("#block-bcldb-content .under-banner");
        private By viewLnks = By.className("views-row");//multi
        private By highlightImg = By.cssSelector(" div > div.highlight-image > img");//multi
        private By highlightTxtH = By.cssSelector(" div > div.highlight-text > h3 > a");//multi
        private By highlightTxt = By.cssSelector(" div > div.highlight-text > a");//multi


        private By footerDiv = By.id("footer#footer");
        private By footerLinks = By.cssSelector("#block-bcldb-footer > ul > li");
        //"Related Websites"
        private By h2FooterMenuLbl = By.cssSelector("h2#block-bcldb-footer-menu");
        private By footerMenu1Items = By.cssSelector(".menu__item");//many, need to chained


        // Page Actions
        public String getLogoImageSource(){
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(logoImg));
                String actualLogoSrc = el.getAttribute("src");
                return actualLogoSrc;
        }

        //ToDo: may remove
        public void selectDoingBizLink(){
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(BizLiquorLnk));
                el.click();
        }

        public AboutPage selectAboutLink(){
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(AboutLnk));
                el.click();
                return new AboutPage(driver);
        }

        //ToDo: may remove
        public void selectDoingBizCannabisLink(){
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(BizCannabisLnk));
                el.click();
        }

        public int getSwiperSlidesCount(){
                //get error of waiting all slides
                //List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(swiperSlides));
                wait.until(ExpectedConditions.visibilityOfElementLocated(swiper));
                List<WebElement> els = driver.findElements(swiperSlides);
                int slidesCount = els.size();
                System.out.println("slidesCount---"+ slidesCount);
                return slidesCount;
        }

        public int getSwiperBulletImgsCount(){
                List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(swiperBulletImgs));
                int bulletImgsCount = els.size();
                System.out.println("bulletImgsCount---"+ bulletImgsCount);
                return bulletImgsCount;
        }

        public int getAriaCurrentTrueCountOfSwiperBulletImgs() {
                List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(swiperBulletImgs));
                String ariaCurrent = null;
                List<String> ariaCurrents = new ArrayList<>();
                for (WebElement el : els) {
                        ariaCurrent = el.getAttribute("aria-current");//can be null
                        ariaCurrents.add(ariaCurrent);
                }

                int nonNullCount = 0;
                for (Object obj : ariaCurrents) {
                        if (obj != null) {
                                nonNullCount++;
                        }
                }
                return nonNullCount;
        }

        public void clickSwiperBulletImg(int i){
                List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(swiperBulletImgs));
                System.out.println("bulletImgs index---"+ i);
                if (i<= els.size()-1){
                    els.get(i).click();
                } else {
                        throw new IllegalArgumentException("wrong index of the bullet images list");
                }
        }

        public void clickSwiperPlayPauseButton(){
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(swiperPlayPauseBtn));
                el.click();
        }

        public void clickSwiperNextOrPrevButton(String nextOrPrevious){
                WebElement nextOrPreviousBtn = null;

                if (nextOrPrevious == "Next") {
                        nextOrPreviousBtn = wait.until(ExpectedConditions.elementToBeClickable(swiperNextBtn));
                } else if (nextOrPrevious == "Previous") {
                        nextOrPreviousBtn = wait.until(ExpectedConditions.elementToBeClickable(swiperPrevBtn));
                } else {
                        throw new IllegalArgumentException("The input string of NextOrPrevious button is wrong");
                }
                nextOrPreviousBtn.click();
        }

        public String getActiveSlideCaptionTitle(){
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(activeSlide));
                String activeSlideCaptionTitle = el.findElement(slideCaption).findElement(slideCaptionTitle).getText();
                return activeSlideCaptionTitle;
        }

        public String getUnderBannerText(){
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(underBannerLbl));
                String actualUnderBannerText = el.getText();
                return actualUnderBannerText;
        }

        //get certain view link UI element
        private WebElement getViewLinkElement(int i){
                List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(viewLnks));
                WebElement viewLink = els.get(i);
                return viewLink;
        }

        //ToDo: refactor to add ViewLink index?
        public String getRetailsViewLinkImageSource(){
                String viewImgSrc = getViewLinkElement(0).findElement(highlightImg).getAttribute("src");
                return viewImgSrc;
        }

        public String getRetailsViewLinkTextHeader(){
                String viewTxtHeader = getViewLinkElement(0).findElement(highlightTxtH).getText();
                return viewTxtHeader;
        }

        public String getRetailsViewLinkText(){
                String viewTxt = getViewLinkElement(0).findElement(highlightTxt).getText();
                return viewTxt;
        }

        public void selectRetailsViewLink(){
                getViewLinkElement(0).findElement(highlightTxtH).click();
        }

        private void javaScriptExecutorScrollToViewCenterAndClick(WebElement el){
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                //move to screen
                executor.executeScript("arguments[0].scrollIntoView({block: 'center'});",el );
                // Bypasses Selenium's cursor restrictions and forces a click
                executor.executeScript("arguments[0].click();", el );
        }

        public BcLiquorStoresPage selectBcLiquorStoresFooterLink(){
                List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(footerLinks));
                WebElement el = els.get(2).findElement(By.cssSelector(" a"));
                javaScriptExecutorScrollToViewCenterAndClick(el);
                return new BcLiquorStoresPage(driver);
        }

        public BcCannabisStoresPage selectBcCannabisStoresFooterLink(){
                List<WebElement> els = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(footerLinks));
                WebElement el = els.get(0).findElement(By.cssSelector(" a"));
                javaScriptExecutorScrollToViewCenterAndClick(el);
                return new BcCannabisStoresPage(driver);
        }

}
