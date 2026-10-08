package com.digishield.e2e.tests;

import com.digishield.e2e.base.BaseTest;
import com.digishield.e2e.pages.DashboardPage;
import com.digishield.e2e.pages.LoginPage;
import com.digishield.e2e.pages.WatchlistPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WatchlistE2ETest extends BaseTest {

    private WatchlistPage watchlistPage;

    @BeforeEach
    public void setupTest() {
        // Dang nhap truoc khi thuc hien cac kiem thu Watchlist
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL + "/login");
        loginPage.login("admin@digishield.com", "Password@123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        watchlistPage = dashboardPage.navigateToWatchlist();
    }

    @Test
    @DisplayName("Thêm mới tài khoản nghi vấn vào Watchlist thành công")
    public void testAddSuspiciousAccount() {
        String testAccount = "ACC_998877";
        String reason = "Giao dịch bất thường giá trị cao trong đêm";
        String riskLevel = "HIGH";

        watchlistPage.addSuspiciousAccount(testAccount, reason, riskLevel);

        Assertions.assertTrue(watchlistPage.isAccountInWatchlist(testAccount),
                "Tài khoản nghi vấn phải xuất hiện trong danh sách sau khi thêm.");
    }

    @Test
    @DisplayName("Tìm kiếm tài khoản nghi vấn trong Watchlist")
    public void testSearchSuspiciousAccount() {
        String searchKeyword = "ACC_998877";
        watchlistPage.searchAccount(searchKeyword);

        Assertions.assertTrue(watchlistPage.isAccountInWatchlist(searchKeyword),
                "Kết quả tìm kiếm phải hiển thị tài khoản khớp với từ khóa.");
    }

    @Test
    @DisplayName("Xóa tài khoản khỏi Watchlist thành công")
    public void testRemoveSuspiciousAccount() {
        String targetAccount = "ACC_998877";

        if (watchlistPage.isAccountInWatchlist(targetAccount)) {
            watchlistPage.removeAccount(targetAccount);
            Assertions.assertFalse(watchlistPage.isAccountInWatchlist(targetAccount),
                    "Tài khoản không còn xuất hiện trong Watchlist sau khi xóa.");
        }
    }
}
