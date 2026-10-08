package com.digishield.e2e.tests;

import com.digishield.e2e.base.BaseTest;
import com.digishield.e2e.pages.ForgotPasswordPage;
import com.digishield.e2e.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ForgotPasswordE2ETest extends BaseTest {

    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;

    @BeforeEach
    public void setupTest() {
        loginPage = new LoginPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        loginPage.open(LoginPage.URL);
    }

    @Test
    @DisplayName("TC-LOGIN-002 - Mở chức năng quên mật khẩu")
    public void testOpenForgotPasswordPage() {
        loginPage.clickForgotPassword();

        Assertions.assertAll(
                () -> Assertions.assertEquals(ForgotPasswordPage.URL,
                        driver.getCurrentUrl(),
                        "URL trang quên mật khẩu không đúng."),
                () -> Assertions.assertEquals(ForgotPasswordPage.TITLE,
                        driver.getTitle(),
                        "Tiêu đề trang quên mật khẩu không đúng."),
                () -> Assertions.assertTrue(forgotPasswordPage.isSecurityCodeFieldDisplayed(),
                        "Trường Mã bảo mật phải được hiển thị."),
                () -> Assertions.assertTrue(forgotPasswordPage.isEmailFieldDisplayed(),
                        "Trường Địa chỉ Email phải được hiển thị."),
                () -> Assertions.assertTrue(forgotPasswordPage.isUpdateButtonDisplayed(),
                        "Nút Cập nhật phải được hiển thị."),
                () -> Assertions.assertTrue(forgotPasswordPage.isBackToLoginLinkDisplayed(),
                        "Liên kết Trở lại đăng nhập phải được hiển thị."),
                () -> Assertions.assertTrue(forgotPasswordPage.isPageDisplayed(),
                        "Trang quên mật khẩu chưa hiển thị đầy đủ."),
                () -> Assertions.assertTrue(forgotPasswordPage.isOnForgotPasswordPage(),
                        "Người dùng chưa ở đúng trang quên mật khẩu."));
    }
}
