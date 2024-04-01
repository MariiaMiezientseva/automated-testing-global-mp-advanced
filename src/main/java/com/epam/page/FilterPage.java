package com.epam.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.epam.constant.Constants.*;

public class FilterPage {
    private final Logger logger = LogManager.getLogger(FilterPage.class);
    private final SelenideElement addFilterButton = $(By.xpath(".//*[contains(@class, 'ghostButton__ghost-button')]"));
    private final SelenideElement editFilterButton = $(By.xpath(".//span[contains(@class, 'filterName__pencil-icon')]"));
    private final SelenideElement modalFilterNameInputField = $(By.cssSelector("div#modal-root input"));
    private final SelenideElement modalUpdateButton = $(By.cssSelector("div#modal-root div:nth-child(2) > button"));
    private final ElementsCollection deleteFilterButtons = $$(By.xpath(".//*[contains(@class, 'deleteFilterButton__bin-icon')]"));
    private final SelenideElement modalDeleteButton = $(By.xpath(".//button[contains(@class, 'bigButton__color-tomato')]"));
    private final ElementsCollection filterList = $$(By.xpath(".//span[contains(@class, 'filterName__link')]"));
    private final SelenideElement spinningPreloader = $(By.className("spinningPreloader__preloader"));
    private final SelenideElement notification = $(By.xpath(".//div[contains(@class, 'notificationItem__message-container')]"));
    private final SelenideElement filterSwitcher = $(By.xpath(".//label[contains(@class, 'inputSwitcher__input')]"));
    private final SelenideElement filterSwitcherState = $(By.xpath(".//span[contains(@class, 'displayFilter__switcher-label')]"));
    private final SelenideElement thereAreNoFiltersTitle = $(By.xpath(".//div[contains(@class, 'noFiltersBlock__title')]"));

    public void openFilterPage() {
        open(FILTERS_PAGE_URL);
    }

    public void clickAddFilterButton() {
        addFilterButton.click();
        logger.info("Button 'Add filter' is clicked on Filters page");
    }

    public void clickEditFilterButton(String filterName) {
        filterList.findBy(exactText(filterName)).hover();
        actions().moveToElement(editFilterButton).click(editFilterButton).perform();
        logger.info("'Edit filter' icon is clicked");
    }

    public void clickFilterName(String filterName) {
        filterList.findBy(exactText(filterName)).click();
        logger.info("Filter name is clicked");
    }

    public void editFilterName(String name) {
        modalFilterNameInputField.setValue(name);
        modalUpdateButton.click();
        logger.info("Filter name is update to '{}'", name);
        waitUntilNotificationDisappeared(UPDATED_NOTIFICATION);
    }

    public void navigateToAndClickFilterSwitcher() {
        actions().moveToElement(filterSwitcher).click(filterSwitcher).perform();
        logger.info("Navigate to and click filter switcher");
    }

    public void deleteFilters() {
        waitForSpinningPreloaderDisappear();
        if (!thereAreNoFiltersTitle.isDisplayed()) {
            for (SelenideElement deleteButton : deleteFilterButtons) {
                executeJavaScript("arguments[0].click();", deleteButton);
                modalDeleteButton.click();
                waitUntilNotificationDisappeared(DELETED_NOTIFICATION);
                logger.info("Filter was deleted successfully");
            }
        }
    }

    public void waitUntilNotificationDisappeared(String message) {
        logger.info("Wait for '{}' pop-up message is disappeared", message);
        notification.shouldHave(Condition.exactText(message)).should(disappear, TEN_SEC);
    }

    public boolean isAddedFilterDisplayed(String name) {
        waitForSpinningPreloaderDisappear();
        filterList.should(sizeGreaterThanOrEqual(1));
        return filterList.findBy(exactText(name)).isDisplayed();
    }

    public boolean hasFilterState(String state) {
        filterSwitcherState.shouldHave(exactText(state));
        return filterSwitcherState.getText().equals(state);
    }

    public boolean isFiltersListEmpty() {
        return filterList.isEmpty();
    }

    private void waitForSpinningPreloaderDisappear() {
        logger.info("Wait for spinning page pre-loader is disappeared");
        spinningPreloader.should(disappear, TEN_SEC);
    }
}
