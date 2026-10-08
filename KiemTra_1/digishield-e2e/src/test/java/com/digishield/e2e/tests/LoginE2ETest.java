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
    @DisplayName("[TC-LOGIN-003] Hiển thị đúng các thành phần hỗ trợ đăng nhập")
    public void testLoginPageActionsAreAvailable() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(loginPage.isLoginPageDisplayed()),
                () -> Assertions.assertTrue(loginPage.isForgotPasswordLinkDisplayed()),
                () -> Assertions.assertTrue(loginPage.isUtcEmailLoginDisplayed()));
    }

    @Test
    @DisplayName("[TC-LOGIN-004] Chọn tùy chọn ghi nhớ đăng nhập")
    public void testRememberMeCanBeSelected() {
        Assertions.assertFalse(loginPage.isRememberMeSelected(),
                "Tùy chọn ghi nhớ đăng nhập phải bỏ chọn khi mới mở trang.");

        loginPage.selectRememberMe();

        Assertions.assertTrue(loginPage.isRememberMeSelected(),
                "Tùy chọn ghi nhớ đăng nhập phải được chọn sau khi người dùng thao tác.");
    }

    @Test
    @DisplayName("[TC-LOGIN-005] Kiểm tra liên kết đăng nhập bằng e-mail UTC")
    public void testUtcEmailLoginLink() {
        Assertions.assertTrue(loginPage.isUtcEmailLoginDisplayed(),
                "Liên kết đăng nhập bằng e-mail UTC phải được hiển thị.");
        Assertions.assertTrue(loginPage.getUtcEmailLoginHref().startsWith(
                        "https://accounts.google.com/o/oauth2/auth"),
                "Liên kết e-mail UTC phải trỏ tới luồng OAuth của Google.");
    }

    @Test
    @DisplayName("[TC-LOGIN-006] Đăng nhập với thông tin không hợp lệ vẫn ở trang đăng nhập")
    public void testLoginFailure_InvalidCredentials() {
        loginPage.login("automation.invalid@example.com", "WrongPassword!123");

        Assertions.assertAll(
                () -> Assertions.assertTrue(loginPage.isOnLoginPage(),
                        "Đăng nhập sai không được điều hướng khỏi trang đăng nhập."),
                () -> Assertions.assertTrue(loginPage.isLoginPageDisplayed(),
                        "Form đăng nhập phải tiếp tục khả dụng sau khi đăng nhập sai."));
    }

    @Test
    @DisplayName("[TC-LOGIN-007] Mật khẩu được hiển thị dưới dạng ký tự ẩn")
    public void testPasswordFieldIsMasked() {
        Assertions.assertEquals("password", loginPage.getPasswordInputType(),
                "Trường mật khẩu phải sử dụng kiểu password.");
    }

    @Test
    @DisplayName("[TC-LOGIN-012] Kiểm tra tiêu đề trang đăng nhập")
    public void testLoginPageTitle() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(LoginPage.URL, driver.getCurrentUrl()),
                () -> Assertions.assertEquals("Đăng nhập", driver.getTitle(),
                        "Tiêu đề trang đăng nhập không đúng."));
    }

    @Test
    @DisplayName("[TC-LOGIN-013] Giá trị nhập vào được giữ trong form đăng nhập")
    public void testLoginFormRetainsEnteredValues() {
        loginPage.enterUsername("test.user@example.com");
        loginPage.enterPassword("TestPassword!123");

        Assertions.assertAll(
                () -> Assertions.assertEquals("test.user@example.com",
                        loginPage.getUsernameValue()),
                () -> Assertions.assertEquals("TestPassword!123",
                        loginPage.getPasswordValue()));
    }

    @Test
    @DisplayName("[TC-LOGIN-014] Thao tác chọn ghi nhớ đăng nhập có tính idempotent")
    public void testRememberMeSelectionIsIdempotent() {
        loginPage.selectRememberMe();
        loginPage.selectRememberMe();

        Assertions.assertTrue(loginPage.isRememberMeSelected(),
                "Checkbox phải vẫn được chọn sau nhiều lần gọi thao tác chọn.");
    }

    @Test
    @DisplayName("[TC-LOGIN-015] Kiểm tra liên kết trợ giúp và phản hồi")
    public void testSupportLinks() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(loginPage.isHelpCenterLinkDisplayed()),
                () -> Assertions.assertEquals("http://hotrokythuat.utc.edu.vn/",
                        loginPage.getHelpCenterHref()),
                () -> Assertions.assertTrue(loginPage.isFeedbackLinkDisplayed()),
                () -> Assertions.assertEquals("mailto:hotrokythuat@utc.edu.vn",
                        loginPage.getFeedbackHref()));
    }

    @Test
    @DisplayName("[TC-LOGIN-008] Đăng nhập thành công với credential do người chạy cung cấp")
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
