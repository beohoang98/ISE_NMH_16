# PROJECT PROPOSAL
**Team**: ISE_NMH_16

___

## 1. THÔNG TIN NHÓM

MSSV | Name | Phone | Main Role
--- | --- | --- | ---
1612001 | Hoàng Dân An | 0772 1088 66 | **Team Leader**
1612619 | Nguyễn Bá Thắng | 0162 7901 357 | Architect Designer & Dev
1612008 | Lê Trần Lâm An | 0397 3541 85 | Requirement Analyst & Tester
1612043 | Phan Ngọc Thanh Bình | 0162 8646 767 | Analyst & Developer

---

## 2. PHÁT BIỂU BÀI TOÁN SƠ LƯỢC

---

## 3. GIẢI PHÁP ĐỀ XUẤT

### 3.1 Phần mềm

#### 3.1.1 Danh sách chức năng phần mềm

- Đăng ký / Đăng nhập
- Tùy chọn ngôn ngữ
- **Nhập số tiền income**
- **Nhập số tiều chi tiêu outcome**
- **Được chọn lựa phân loại các income hay outcome đó**
- Cho phép bổ sung phân loại
- **Thống kê** chi tiêu (**outcome**) hay thu nhập (**income**) trong `ngày`/`tuần`/`tháng`/`năm`

#### 3.1.2 Kiến trúc tổng thể phần mềm

- Phần mềm sẽ chạy trên nền hệ điều hành Android
- Các tác vụ xử lý và tính toán đều nằm trên app cùng với giao diện
- Các dữ liệu nhập xuất sẽ được lưu trực tiếp trong bộ nhớ máy
- Khi có kết nối internet, dữ liệu về account và income/outcome sẽ được đồng bộ (sync) lên dịch vụ cloud **Firebase** của Google.


### 3.2 Phần cứng

- Chạy trên nền Android `x.0`
- Sử dụng bộ API `4.0`, tương thích hầu hết các thiết bị

---

## 4. KẾ HOẠCH PHÁT TRIỂN

### 4.1 Phần tích yêu cầu

- **Thời gian:** 2 tuần (8/10/2018 - 21/10/2018)
- **Phụ trách:** Phan Ngọc Thanh Bình, Lê Trần Lâm An
- **Result:**
    - Bản phân tích chức năng - người dùng
    - Bản Sequence Diagram, UseCase Diagram
    - Bản Project Proposal (tuần đầu tiên)

### 4.2 Thiết kế phần mềm

- **Thời gian:** 1 tuần (22/10 - 28/10)
- **Phụ trách thực hiện:** Phan Ngọc Thanh Bình, Nguyễn Bá Thắng
- **Phụ trách reviews:** Lê Trần Lâm An
- **Sản phẩm:**
    - Bản thiết kế các màn hình sử dụng theo use-case đã có
    - Bản thiết kế database và kiến trúc viết app
    - Bản thiết kế layout và fragment theo bản thiết kế use-case

### 4.3 Cài đặt phần mềm (giai đoạn quan trọng)

- **Thời gian:** 3 tuần (29/10 - 18/11)
- **Phụ trách thực hiện:** Nguyễn Bá Thắng
- **Phụ trách hỗ trợ:** Phan Ngọc Thanh Bình, Hoàng Dân An
- **Phụ trách test & reviews:** Lê Trần Lâm An
- **Sản phẩm:**
    - Đáp ứng các chỉ tiêu trong bản phân tích
    - Đáp ứng các chỉ tiêu trong bản thiết kế
    - Hạn chế bugs phát sinh

### 4.4 Kiểm thử phần mềm

Kiểm thử phần mềm sẽ song song với Cài đặt phần mềm (29/10 - 18/11), 1 tuần test & review 1 lần.
- **Trước cài đặt:** có đủ 100 testcases
- **Khi và sau cài đặt:** các bugs phát sinh sẽ thêm vào

### 4.5 Triển khai vào bảo trì

Sau khi hoàn thành bản stable nhất, release sản phẩm lần đầu
- Tester Lê Trần Lâm An sẽ tiếp tục tìm bugs
- Team sẽ đi quảng cáo bạn bè sử dụng thử để collect reviews
- Update documents và fix bugs hàng tuần
- Đến khi nộp deadline thì **ngưng**

---

## 5. NHÂN SỰ VÀ CHI PHÍ
