package com.digishield.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public static final String URL =
            "https://vanphongdientu.utc.edu.vn/Login";
    public static final String EMPTY_USERNAME_MESSAGE =
            "Bạn chưa nhập tên đăng nhập";

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("userpwd");
    private final By rememberMeCheckbox = By.id("persistent");
    private final By rememberMeLabel = By.cssSelector("label.check[for='persistent']");
    private final By loginButton = By.cssSelector("input.submit_login");
    private final By validationMessage = By.cssSelector(
            ".alert-danger, .error-message, .validation-summary-errors, "
                    + ".field-validation-error, [class*='error']");
    private final By forgotPasswordLink = By.cssSelector("a[href*='/Login/GetPass']");
    private final By utcEmailLoginLink = By.cssSelector("a.button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        navigateTo(url);
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void selectRememberMe() {
        if (!driver.findElement(rememberMeCheckbox).isSelected()) {
            click(rememberMeLabel);
        }
    }

    public boolean isRememberMeSelected() {
        return driver.findElement(rememberMeCheckbox).isSelected();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return getText(validationMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(validationMessage);
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    public boolean isForgotPasswordLinkDisplayed() {
        return isDisplayed(forgotPasswordLink);
    }

    public boolean isUtcEmailLoginDisplayed() {
        return isDisplayed(utcEmailLoginLink);
    }

    public String getUtcEmailLoginHref() {
        return waitForVisibility(utcEmailLoginLink).getAttribute("href");
    }

    public String getPasswordInputType() {
        return waitForVisibility(passwordField).getAttribute("type");
    }

    public boolean isUsernameFieldDisplayed() {
        return isDisplayed(usernameField);
    }

    public boolean isPasswordFieldDisplayed() {
        return isDisplayed(passwordField);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameField)
                && isDisplayed(passwordField)
                && isDisplayed(loginButton);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().equals(URL);
    }
}
