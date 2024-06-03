package com.epam.ui;

import com.codeborne.selenide.testng.ScreenShooter;
import com.epam.page.FilterPage;
import com.epam.page.WelcomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.UUID;

import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.epam.constant.Constants.SIGNED_IN_NOTIFICATION;

public class BaseTest {
    private final Logger logger = LogManager.getLogger(BaseTest.class);
    private final WelcomePage welcomePage = new WelcomePage();
    private final FilterPage filterPage = new FilterPage();
    protected String filterName;

    @BeforeMethod
    public void openRPFiltersPageAndLogIn() {
        ScreenShooter.captureSuccessfulTests = false;
        filterPage.openFilterPage();
        logger.info("ReportPortal welcome page is opened in Chrome web browser by given url");
        welcomePage.logInToRP();
        filterPage.waitUntilNotificationDisappeared(SIGNED_IN_NOTIFICATION);
        logger.info("User successfully logged in");
        filterName = UUID.randomUUID().toString();
    }

    @AfterMethod
    public void deleteCreatedFiltersAndCloseChromeDriver() {
        filterPage.deleteFilters();
        closeWebDriver();
        logger.info("Webdriver is closed");
    }

    protected void resizeWindow() {
        logger.info("Before resize browser size is: {}", browserSize);
        browserSize = "8000x8000";
        logger.info("After resize browser size is: {}", browserSize);
    }

    protected void resizeWindowToDefault() {
        logger.info("Before resize browser size is: {}", browserSize);
        browserSize = "1366x768";
        logger.info("After resize browser size is: {}", browserSize);
    }
}
