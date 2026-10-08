package com.digishield.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WatchlistPage extends BasePage {

    // Locators
    private final By pageHeading = By.cssSelector("h2, .page-title");
    private final By searchInput = By.id("search-watchlist");
    private final By addAccountButton = By.id("btn-add-watchlist");
    private final By accountNumberInput = By.id("account-number");
    private final By riskReasonInput = By.id("risk-reason");
    private final By riskLevelSelect = By.id("risk-level");
    private final By saveButton = By.id("btn-save-account");
    private final By watchlistTable = By.id("watchlist-table");
    private final By tableRows = By.cssSelector("#watchlist-table tbody tr");
    private final By alertSuccess = By.cssSelector(".alert-success, .toast-success");

    public WatchlistPage(WebDriver driver) {
        super(driver);
    }

    public boolean isWatchlistPageDisplayed() {
        return isDisplayed(pageHeading) || isDisplayed(watchlistTable);
    }

    public void searchAccount(String keyword) {
        type(searchInput, keyword);
    }

    public void clickAddAccount() {
        click(addAccountButton);
    }

    public void fillAccountDetails(String accountNumber, String reason, String riskLevel) {
        type(accountNumberInput, accountNumber);
        type(riskReasonInput, reason);
        if (riskLevel != null && !riskLevel.isEmpty()) {
            type(riskLevelSelect, riskLevel);
        }
    }

    public void clickSave() {
        click(saveButton);
    }

    public void addSuspiciousAccount(String accountNumber, String reason, String riskLevel) {
        clickAddAccount();
        fillAccountDetails(accountNumber, reason, riskLevel);
        clickSave();
    }

    public boolean isAccountInWatchlist(String accountNumber) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    public void removeAccount(String accountNumber) {
        By removeBtnLocator = By.xpath(String.format("//tr[contains(., '%s')]//button[contains(@class, 'btn-delete') or contains(text(), 'Xóa') or contains(text(), 'Delete')]", accountNumber));
        click(removeBtnLocator);
    }

    public int getWatchlistRowCount() {
        return driver.findElements(tableRows).size();
    }

    public boolean isSuccessAlertDisplayed() {
        return isDisplayed(alertSuccess);
    }
}
