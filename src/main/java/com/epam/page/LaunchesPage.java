package com.epam.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.epam.constant.Constants.*;

public class LaunchesPage {
    private final Logger logger = LogManager.getLogger(LaunchesPage.class);
    private final SelenideElement saveFilterButton = $(By.cssSelector("div:nth-child(4) > button > span"));
    private final SelenideElement launchNameField = $(By.cssSelector("div > div > input"));
    private final SelenideElement modalFilterNameInputField = $(By.cssSelector("div#modal-root input"));
    private final SelenideElement modalAddButton = $(By.cssSelector("div#modal-root div:nth-child(2) > button"));
    private final ElementsCollection filterItems = $$(By.xpath(".//span[contains(@class, 'filterItem__name')]"));
    private final SelenideElement notification = $(By.cssSelector("#notification-root > div > div > div > div > p"));
    private final SelenideElement cloneButton = $(By.cssSelector("div.filtersActionBar__filters-action-bar--fldP7 div:nth-child(2) > button"));

    public void openLaunchesPage() {
        open(LAUNCHES_PAGE_URL);
    }

    public void enterLaunchName(String value) {
        launchNameField.setValue(value);
        logger.info("Launch name '{}' is set", value);
    }

    public void clickSaveButton() {
        saveFilterButton.click();
        logger.info("Save button is clicked");
    }

    public void clickCloneButton() {
        cloneButton.click();
        logger.info("Clone button is clicked");
    }

    public void clickModalAddButton() {
        modalAddButton.click();
        logger.info("Add button is clicked");
        waitUntilNotificationDisappeared();
    }

    public void setFilterName(String name) {
        modalFilterNameInputField.setValue(name);
        logger.info("Filter  name '{}' is set", name);
        clickModalAddButton();
    }

    public boolean isLaunchPageDisplayed() {
        return url().contains("launches");
    }

    public boolean isAddedFilterDisplayed(String name) {
        return filterItems.findBy(Condition.exactText(name)).isDisplayed();
    }

    private void waitUntilNotificationDisappeared() {
        logger.info("Wait for '{}' pop-up message is disappeared", SAVED_NOTIFICATION);
        notification.shouldHave(exactText(SAVED_NOTIFICATION)).should(Condition.disappear, TEN_SEC);
    }
}
