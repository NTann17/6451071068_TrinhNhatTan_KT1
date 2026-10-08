package com.digishield.e2e.tests;

import com.digishield.e2e.base.BaseTest;
import com.digishield.e2e.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginE2ETest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupTest() {
        loginPage = new LoginPage(driver);
        loginPage.open(LoginPage.URL);
    }

    @Test
    @DisplayName("TC-LOGIN-001 - Đăng nhập khi bỏ trống thông tin")
    public void testLoginFailure_EmptyFields() {
        loginPage.clickLogin();

        Assertions.assertAll(
                () -> Assertions.assertTrue(loginPage.isErrorMessageDisplayed(),
                        "Trang phải hiển thị thông báo validation khi bỏ trống tên đăng nhập."),
                () -> Assertions.assertEquals(LoginPage.EMPTY_USERNAME_MESSAGE,
                        loginPage.getErrorMessage().trim(),
                        "Thông báo validation không đúng."),
                () -> Assertions.assertTrue(loginPage.isLoginPageDisplayed(),
                        "Người dùng vẫn phải ở trang đăng nhập."),
                () -> Assertions.assertTrue(loginPage.isOnLoginPage(),
                        "URL không còn là trang đăng nhập."));
    }

    @Test
    @DisplayName("Hiển thị đúng các thành phần hỗ trợ đăng nhập")
    public void testLoginPageActionsAreAvailable() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(loginPage.isLoginPageDisplayed()),
                () -> Assertions.assertTrue(loginPage.isForgotPasswordLinkDisplayed()),
                () -> Assertions.assertTrue(loginPage.isUtcEmailLoginDisplayed()));
    }

    @Test
    @DisplayName("Đăng nhập thành công với credential do người chạy cung cấp")
    public void testLoginSuccessWithConfiguredCredentials() {
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        Assumptions.assumeTrue(username != null && !username.isBlank()
                        && password != null && !password.isBlank(),
                "Bỏ qua: truyền -Dusername=... -Dpassword=... để chạy smoke test đăng nhập thành công.");

        loginPage.login(username, password);

        Assertions.assertFalse(loginPage.isLoginPageDisplayed(),
                "Sau khi đăng nhập thành công, trang đăng nhập không còn hiển thị.");
    }
}
