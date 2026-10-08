package com.digishield.e2e.tests;

import com.digishield.e2e.base.BaseTest;
import com.digishield.e2e.pages.DashboardPage;
import com.digishield.e2e.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginE2ETest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupTest() {
        loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL + "/login");
    }

    @Test
    @DisplayName("Đăng nhập thành công với thông tin tài khoản hợp lệ")
    public void testLoginSuccess() {
        loginPage.login("admin@digishield.com", "Password@123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assertions.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Dashboard phải được hiển thị sau khi đăng nhập thành công.");
    }

    @Test
    @DisplayName("Đăng nhập thất bại khi sai mật khẩu")
    public void testLoginFailure_InvalidCredentials() {
        loginPage.login("admin@digishield.com", "WrongPassword");

        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Thông báo lỗi phải hiển thị khi đăng nhập sai thông tin.");
    }

    @Test
    @DisplayName("Đăng nhập thất bại khi để trống trường thông tin")
    public void testLoginFailure_EmptyFields() {
        loginPage.login("", "");

        Assertions.assertTrue(loginPage.isLoginPageDisplayed(),
                "Người dùng vẫn ở trang đăng nhập khi gửi form rỗng.");
    }
}
