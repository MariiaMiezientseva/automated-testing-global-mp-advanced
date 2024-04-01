package com.epam.page;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {
    private final Logger logger = LogManager.getLogger(WelcomePage.class);
    private final SelenideElement logInField = $(By.xpath(".//form/div[1]//input"));
    private final SelenideElement passwordField = $(By.xpath(".//form/div[2]//input"));
    private final SelenideElement loginButton = $(By.xpath(".//form/div[3]/button"));

    public void logInToRP() {
        enterLogin();
        enterPassword();
        clickLoginButton();
    }

    private void enterLogin() {
        logInField.setValue(System.getenv("RP_USERNAME"));
        logger.info("Login entered");
    }

    private void enterPassword() {
        passwordField.setValue(System.getenv("RP_PASSWORD"));
        logger.info("Password entered");
    }

    private void clickLoginButton() {
        loginButton.click();
        logger.info("Login button clicked");
    }
}
