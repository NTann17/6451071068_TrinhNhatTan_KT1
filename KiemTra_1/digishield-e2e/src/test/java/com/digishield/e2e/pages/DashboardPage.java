package com.digishield.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // Locators
    private final By headerTitle = By.cssSelector("header h1, .header-title");
    private final By userProfileName = By.cssSelector(".user-profile .user-name, #user-name");
    private final By userAvatar = By.cssSelector(".user-avatar, img.avatar");
    private final By logoutButton = By.id("logout-btn");
    private final By watchlistMenuLink = By.cssSelector("nav a[href*='watchlist'], #nav-watchlist");
    private final By dashboardMenuLink = By.cssSelector("nav a[href*='dashboard'], #nav-dashboard");
    private final By statsCard = By.cssSelector(".stats-card, .dashboard-metrics");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        return isDisplayed(headerTitle) || isDisplayed(userProfileName) || isDisplayed(statsCard);
    }

    public String getUserProfileName() {
        return getText(userProfileName);
    }

    public String getHeaderTitle() {
        return getText(headerTitle);
    }

    public boolean isUserAvatarDisplayed() {
        return isDisplayed(userAvatar);
    }

    public void clickLogout() {
        click(logoutButton);
    }

    public WatchlistPage navigateToWatchlist() {
        click(watchlistMenuLink);
        return new WatchlistPage(driver);
    }
}
