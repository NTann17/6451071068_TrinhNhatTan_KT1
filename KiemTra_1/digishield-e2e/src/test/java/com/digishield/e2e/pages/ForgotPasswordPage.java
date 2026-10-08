package com.digishield.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    public static final String URL =
            "https://vanphongdientu.utc.edu.vn/Login/GetPass";
    public static final String TITLE = "Lấy lại mật khẩu";

    private final By securityCodeField = By.name("captcha");
    private final By emailField = By.name("email");
    private final By updateButton = By.cssSelector("input[type='submit']");
    private final By backToLoginLink = By.cssSelector("a[href='/Login']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageDisplayed() {
        return isDisplayed(securityCodeField)
                && isDisplayed(emailField)
                && isDisplayed(updateButton)
                && isDisplayed(backToLoginLink);
    }

    public boolean isSecurityCodeFieldDisplayed() {
        return isDisplayed(securityCodeField);
    }

    public boolean isEmailFieldDisplayed() {
        return isDisplayed(emailField);
    }

    public boolean isUpdateButtonDisplayed() {
        return isDisplayed(updateButton);
    }

    public boolean isBackToLoginLinkDisplayed() {
        return isDisplayed(backToLoginLink);
    }

    public boolean isOnForgotPasswordPage() {
        return getCurrentUrl().equals(URL);
    }
}
