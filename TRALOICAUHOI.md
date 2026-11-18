## Cách chạy
1. Tạo DB student_db, course_db trong PostgreSQL
2. Chạy theo thứ tự: eureka-server, course-service, student-service, api-gateway
3. Gọi API qua gateway: http://localhost:8080

## Trả lời Task 3
1. Các service tự đăng ký tên và địa chỉ vào Service Registry (Eureka) khi khởi động và gửi heartbeat định kỳ. Gateway và Feign hỏi registry theo tên logic để lấy danh sách instance còn sống. Không nên hardcode IP/Port vì địa chỉ thay đổi liên tục, phải sửa cấu hình mỗi khi hạ tầng thay đổi
2. Route dùng lb://student-service nên Spring Cloud LoadBanlacer tự phân phối request tới mọi instance, không cần sửa Gateway.
3. So sánh OpenFeign và Kafka
- OpenFeign:
  + Độ phức tạp: Thấp, gọi như hàm
  + Phản hồi: Biết ngay còn chỗ hay không để trả về cho sinh viên
  + Chịu tải: Course chậm, chết kéo theo Student
- Kafka:
  + Độ phức tạp: Cao, kafka có broker, producer/consumer
  + Phản hồi: Phải để trạng thái chờ rồi cập nhật sau