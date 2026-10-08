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

    @Test
    @DisplayName("[TC-LOGIN-009] Quay lại trang đăng nhập từ chức năng quên mật khẩu")
    public void testBackToLogin() {
        loginPage.clickForgotPassword();
        forgotPasswordPage.clickBackToLogin();

        Assertions.assertAll(
                () -> Assertions.assertEquals(LoginPage.URL, driver.getCurrentUrl(),
                        "Liên kết quay lại phải điều hướng tới trang đăng nhập."),
                () -> Assertions.assertTrue(loginPage.isLoginPageDisplayed(),
                        "Form đăng nhập phải hiển thị sau khi quay lại."));
    }

    @Test
    @DisplayName("[TC-LOGIN-010] Submit form lấy lại mật khẩu khi bỏ trống thông tin")
    public void testForgotPasswordEmptyFields() {
        loginPage.clickForgotPassword();
        forgotPasswordPage.clickUpdate();

        Assertions.assertAll(
                () -> Assertions.assertTrue(forgotPasswordPage.isOnForgotPasswordPage(),
                        "Submit dữ liệu rỗng không được điều hướng khỏi trang lấy lại mật khẩu."),
                () -> Assertions.assertTrue(forgotPasswordPage.isPageDisplayed(),
                        "Form lấy lại mật khẩu phải tiếp tục hiển thị để người dùng nhập lại."));
    }

    @Test
    @DisplayName("[TC-LOGIN-011] Kiểm tra kiểu dữ liệu các trường lấy lại mật khẩu")
    public void testForgotPasswordFieldTypes() {
        loginPage.clickForgotPassword();

        Assertions.assertAll(
                () -> Assertions.assertEquals("text",
                        forgotPasswordPage.getSecurityCodeInputType(),
                        "Trường mã bảo mật phải là ô nhập văn bản."),
                () -> Assertions.assertEquals("text",
                        forgotPasswordPage.getEmailInputType(),
                        "Trường email phải là ô nhập văn bản."));
    }

    @Test
    @DisplayName("[TC-LOGIN-016] Kiểm tra ảnh mã bảo mật trên trang lấy lại mật khẩu")
    public void testCaptchaImage() {
        loginPage.clickForgotPassword();

        Assertions.assertAll(
                () -> Assertions.assertTrue(forgotPasswordPage.isCaptchaImageDisplayed(),
                        "Ảnh mã bảo mật phải được hiển thị."),
                () -> Assertions.assertTrue(
                        forgotPasswordPage.getCaptchaImageSource()
                                .contains("/login/index/captcha"),
                        "Ảnh phải sử dụng endpoint captcha của hệ thống."));
    }
}
