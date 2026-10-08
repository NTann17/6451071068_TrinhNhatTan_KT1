# DigiShield E2E Test Project

Bộ kiểm thử tự động End-to-End (E2E) cho website **Văn phòng điện tử UTC**:

- Trang đăng nhập: <https://vanphongdientu.utc.edu.vn/Login>
- Trang lấy lại mật khẩu: <https://vanphongdientu.utc.edu.vn/Login/GetPass>

Project sử dụng Selenium WebDriver, JUnit 5, Maven và mô hình Page Object Model (POM). Kết quả kiểm thử được tích hợp với Allure Report.

## 1. Mục tiêu

Project kiểm tra các luồng chính trên website:

- Hiển thị và validation của form đăng nhập.
- Đăng nhập với dữ liệu không hợp lệ.
- Đăng nhập thành công khi cung cấp credential hợp lệ.
- Tùy chọn ghi nhớ đăng nhập.
- Đăng nhập bằng email UTC qua OAuth.
- Liên kết quên mật khẩu, trợ giúp và phản hồi.
- Hiển thị form lấy lại mật khẩu.
- Kiểm tra ảnh captcha và các trường dữ liệu của form lấy lại mật khẩu.

Danh sách và chi tiết các bước kiểm thử được lưu trong [Test-Cases.xlsx](./test-cases/Test-Cases.xlsx).

## 2. Công nghệ sử dụng

| Thành phần | Phiên bản |
|---|---:|
| Java | 17 |
| Maven | 3.x |
| Selenium Java | 4.20.0 |
| JUnit Jupiter | 5.10.2 |
| WebDriverManager | 5.8.0 |
| Allure JUnit 5 | 2.29.1 |
| Allure Maven Plugin | 2.15.0 |

## 3. Cấu trúc project

```text
digishield-e2e/
├── pom.xml
├── README.md
├── src/
│   └── test/
│       └── java/com/digishield/e2e/
│           ├── base/
│           │   └── BaseTest.java
│           ├── pages/
│           │   ├── BasePage.java
│           │   ├── LoginPage.java
│           │   ├── ForgotPasswordPage.java
│           │   ├── DashboardPage.java
│           │   └── WatchlistPage.java
│           └── tests/
│               ├── LoginE2ETest.java
│               ├── ForgotPasswordE2ETest.java
│               └── WatchlistE2ETest.java
└── test-cases/
    └── Test-Cases.xlsx
```

### Vai trò các thành phần chính

- `BaseTest.java`: khởi tạo và đóng Chrome WebDriver trước/sau mỗi testcase.
- `BasePage.java`: các thao tác dùng chung trên page object.
- `LoginPage.java`: locator và thao tác của trang đăng nhập.
- `ForgotPasswordPage.java`: locator và thao tác của trang lấy lại mật khẩu.
- `LoginE2ETest.java`: các testcase `TC-LOGIN-001`, `TC-LOGIN-003` đến `TC-LOGIN-008`, `TC-LOGIN-012` đến `TC-LOGIN-015`.
- `ForgotPasswordE2ETest.java`: các testcase `TC-LOGIN-002`, `TC-LOGIN-009` đến `TC-LOGIN-011` và `TC-LOGIN-016`.
- `Test-Cases.xlsx`: tài liệu quản lý testcase, gồm phần tổng hợp và chi tiết bước kiểm thử.

## 4. Yêu cầu cài đặt

Trước khi chạy, cần cài đặt:

1. JDK 17 hoặc phiên bản tương thích.
2. Apache Maven 3.x.
3. Google Chrome.
4. Kết nối Internet để truy cập website UTC và để WebDriverManager tải ChromeDriver nếu cần.

Kiểm tra môi trường:

```powershell
java -version
mvn -version
```

ChromeDriver không cần cài thủ công. `WebDriverManager` tự động phát hiện phiên bản Chrome và cấu hình driver.

## 5. Cài đặt project

Mở PowerShell tại thư mục project:

```powershell
cd KiemTra_1\digishield-e2e
```

Maven sẽ tự tải các dependency trong lần chạy đầu tiên. Có thể kiểm tra biên dịch test bằng:

```powershell
mvn test-compile
```

## 6. Chạy kiểm thử

### Chạy toàn bộ testcase

```powershell
mvn clean test
```

### Chạy ở chế độ headless

Chế độ này phù hợp với CI/CD hoặc máy không có giao diện đồ họa:

```powershell
mvn clean test -Dheadless=true
```

### Chạy riêng các suite chính

```powershell
mvn clean test -Dheadless=true "-Dtest=LoginE2ETest,ForgotPasswordE2ETest"
```

### Chạy một class hoặc một testcase

```powershell
mvn test -Dtest=LoginE2ETest
mvn test -Dtest=ForgotPasswordE2ETest#testLoginFailure_EmptyFields
```

Tên testcase và phương thức tương ứng có thể xem trong các file dưới [src/test/java](./src/test/java).

## 7. Chạy testcase đăng nhập thành công

Testcase đăng nhập thành công không sử dụng credential cố định trong source code. Cung cấp credential qua system property khi chạy:

```powershell
mvn clean test -Dheadless=true `
  "-Dtest=LoginE2ETest#testLoginSuccessWithConfiguredCredentials" `
  "-Dusername=TEN_DANG_NHAP" `
  "-Dpassword=MAT_KHAU"
```

Nếu không truyền đủ `username` và `password`, testcase sẽ được JUnit đánh dấu `SKIPPED` thay vì thất bại. Không commit credential vào source code, README hoặc file cấu hình của repository.

## 8. Sinh Allure Report

### Bước 1: Chạy test để tạo dữ liệu kết quả

```powershell
mvn clean test -Dheadless=true "-Dtest=LoginE2ETest,ForgotPasswordE2ETest"
```

Dữ liệu Allure được ghi vào:

```text
target/allure-results/
```

### Bước 2: Tạo HTML report

```powershell
mvn io.qameta.allure:allure-maven:2.15.0:report
```

Report được tạo tại:

```text
target/site/allure-maven-plugin/index.html
```

Có thể mở file `index.html` bằng trình duyệt để xem tổng quan, suite, testcase, trạng thái, thời gian thực thi và chi tiết lỗi.

## 9. Kết quả kiểm thử tham khảo

Ở lần chạy gần nhất:

```text
Tổng số: 16 testcase
Passed: 15
Skipped: 1
Failed: 0
Errors: 0
```

Testcase đăng nhập thành công được `SKIPPED` khi chưa truyền credential thật. Kết quả cụ thể có thể thay đổi theo trạng thái website, trình duyệt, mạng và dữ liệu tài khoản.

## 10. Quy trình thêm testcase

Khi thêm testcase mới:

1. Xác định mã testcase và cập nhật [Test-Cases.xlsx](./test-cases/Test-Cases.xlsx).
2. Bổ sung hoặc tái sử dụng locator trong page object tương ứng.
3. Viết testcase trong package `com.digishield.e2e.tests`.
4. Sử dụng `@DisplayName` với mã testcase để Allure và Maven report dễ truy vết.
5. Chạy testcase mới ở chế độ headless.
6. Chạy lại suite liên quan và kiểm tra không có regression.
7. Sinh Allure Report để đối chiếu kết quả.

Không nên đặt locator và logic thao tác trực tiếp trong test nếu logic đó thuộc về một page object.

## 11. Xử lý lỗi thường gặp

### Không tải được ChromeDriver

- Kiểm tra Google Chrome đã được cài đặt.
- Kiểm tra kết nối Internet.
- Xóa cache driver của WebDriverManager nếu cache bị lỗi, sau đó chạy lại Maven.

### Website không truy cập được hoặc testcase timeout

- Kiểm tra URL website UTC trên trình duyệt.
- Kiểm tra mạng nội bộ, proxy hoặc firewall.
- Website có thể thay đổi giao diện hoặc locator; khi đó cần cập nhật page object tương ứng.

### Test đăng nhập thành công bị bỏ qua

Đây là hành vi có chủ đích khi chưa truyền credential:

```powershell
-Dusername=... -Dpassword=...
```

Không dùng tài khoản cá nhân thật trong log CI hoặc commit.

### Maven không nhận lệnh `allure:report`

Gọi plugin bằng đầy đủ groupId, artifactId và version:

```powershell
mvn io.qameta.allure:allure-maven:2.15.0:report
```

## 12. Lưu ý bảo mật và artifact

- Không lưu username/password thật trong source code.
- Không đưa credential vào command history hoặc log CI công khai.
- Các thư mục `target/` và `allure-results/` là artifact sinh ra trong quá trình chạy test.
- Chỉ chia sẻ HTML report khi report không chứa dữ liệu nhạy cảm.
