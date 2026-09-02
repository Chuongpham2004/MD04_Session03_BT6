# Giải pháp ngăn chặn Cascading Failure (Sập dây chuyền) trong Microservices

**Bối cảnh hệ thống:**
Trong quá trình tạo đơn hàng, `Order Service` bắt buộc phải gọi API sang `Product Service` để đồng bộ giá tiền và kiểm tra tồn kho. Nếu `Product Service` phản hồi chậm, quá tải hoặc bị sập, các luồng (threads) bên `Order Service` sẽ bị treo để chờ đợi. Nếu không có cơ chế phòng vệ, `Order Service` sẽ cạn kiệt tài nguyên bộ nhớ và sập theo, gây ra hiệu ứng domino (Cascading Failure).

Để hệ thống chịu lỗi tốt (Fault Tolerance), cần áp dụng các cơ chế sau:

## 1. Timeout Handling (Giới hạn thời gian chờ)
* **Cơ chế:** Thiết lập một mốc thời gian chờ tối đa (ví dụ: 3 giây) cho mỗi request gọi liên dịch vụ.
* **Áp dụng:** Cấu hình `ConnectTimeout` và `ReadTimeout` trực tiếp trên `RestTemplate` hoặc `OpenFeign`.
* **Hiệu quả:** Nếu quá thời gian quy định mà `Product Service` không phản hồi, `Order Service` sẽ chủ động ngắt kết nối. Điều này giúp giải phóng ngay lập tức các luồng xử lý, ngăn tình trạng kẹt hàng đợi và cạn kiệt tài nguyên.

## 2. Circuit Breaker (Cơ chế ngắt mạch)
* **Cơ chế:** Hoạt động như một chiếc cầu chì điện. Nó liên tục giám sát tỷ lệ lỗi của các cuộc gọi API từ `Order` sang `Product`.
* **Áp dụng:** Khi tỷ lệ gọi API thất bại vượt quá ngưỡng an toàn (ví dụ: 50% request lỗi trong 10 giây), mạch sẽ chuyển sang trạng thái "Mở" (Open).
* **Hiệu quả:** `Order Service` sẽ ngay lập tức từ chối/trả về lỗi cho các request mới thay vì cố gắng gọi sang `Product Service`. Điều này chặn đứng lưu lượng mạng dư thừa, giúp `Product Service` có thời gian và không gian bộ nhớ để tự phục hồi.

## 3. Fallback Mechanism (Cơ chế dự phòng)
* **Cơ chế:** Xây dựng một luồng dữ liệu thay thế hoặc một kịch bản "hạ cánh mềm" khi API chính gặp sự cố.
* **Áp dụng:** Kết hợp `try-catch` để bắt các ngoại lệ liên kết mạng (ví dụ: `ResourceAccessException`).
* **Hiệu quả:** Thay vì để ứng dụng ném lỗi 500 (Internal Server Error) ra màn hình người dùng, `Order Service` chủ động trả về một JSON Error chuẩn hóa (ví dụ: `503 - Dịch vụ sản phẩm hiện không khả dụng`), hoặc cung cấp một dữ liệu sản phẩm mặc định (Mock Data / Cache) để luồng nghiệp vụ không bị ngắt hoàn toàn.

## 4. Retry (Thử lại tự động)
* **Cơ chế:** Cho phép hệ thống tự động thực hiện lại request lỗi thêm một vài lần trước khi kích hoạt Fallback.
* **Áp dụng:** Cấu hình thử lại (retry) tối đa 2-3 lần, mỗi lần cách nhau vài giây.
* **Hiệu quả:** Khắc phục hiệu quả các sự cố rớt mạng chập chờn (network glitch) trong tích tắc mà không làm gián đoạn trải nghiệm của người dùng.