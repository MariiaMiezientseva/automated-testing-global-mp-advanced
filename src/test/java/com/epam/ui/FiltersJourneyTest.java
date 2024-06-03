package com.epam.ui;

import com.codeborne.selenide.testng.ScreenShooter;
import com.epam.page.FilterPage;
import com.epam.page.LaunchesPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.epam.constant.Constants.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Listeners({ScreenShooter.class})
public class FiltersJourneyTest extends BaseTest {
    private final FilterPage filterPage = new FilterPage();
    private final LaunchesPage launchesPage = new LaunchesPage();

    @Test
    public void addNewFilter() {
        filterPage.clickAddFilterButton();
        assertTrue(launchesPage.isLaunchPageDisplayed());
        launchesPage.enterLaunchName("UI test");
        launchesPage.clickSaveButton();
        launchesPage.setFilterName(filterName);
        assertTrue(launchesPage.isAddedFilterDisplayed(filterName));
        filterPage.openFilterPage();
        assertTrue(filterPage.isAddedFilterDisplayed(filterName));
    }

    @Test
    public void editFilter() {
        addNewFilter();
        filterPage.clickEditFilterButton(filterName);
        filterPage.editFilterName(UPDATED_FILTER_NAME);
        assertTrue(filterPage.isAddedFilterDisplayed(UPDATED_FILTER_NAME));
    }

    @Test
    public void copyFilter() {
        addNewFilter();
        filterPage.clickFilterName(filterName);
        launchesPage.clickCloneButton();
        launchesPage.clickSaveButton();
        launchesPage.clickModalAddButton();
        assertTrue(launchesPage.isAddedFilterDisplayed(String.format(CLONED_FILTER_NAME, filterName)));
        filterPage.openFilterPage();
        assertTrue(filterPage.isAddedFilterDisplayed(String.format(CLONED_FILTER_NAME, filterName)));
    }

    @Test
    public void disableFilterOnLaunches() {
        resizeWindow();
        closeWebDriver();
        openRPFiltersPageAndLogIn();
        addNewFilter();
        filterPage.navigateToAndClickFilterSwitcher();
        assertTrue(filterPage.hasFilterState(FILTER_OFF));
        launchesPage.openLaunchesPage();
        assertFalse(launchesPage.isAddedFilterDisplayed(filterName));
        resizeWindowToDefault();
    }

    @Test
    public void enableFilterOnLaunches() {
        addNewFilter();
        filterPage.navigateToAndClickFilterSwitcher();
        assertTrue(filterPage.hasFilterState(FILTER_OFF));
        filterPage.navigateToAndClickFilterSwitcher();
        assertTrue(filterPage.hasFilterState(FILTER_ON));
        filterPage.clickFilterName(filterName);
        assertTrue(launchesPage.isAddedFilterDisplayed(filterName));
    }

    @Test
    public void deleteFilters() {
        addNewFilter();
        filterPage.deleteFilters();
        assertTrue(filterPage.isFiltersListEmpty());
    }
}