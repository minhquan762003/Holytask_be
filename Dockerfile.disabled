# Sử dụng image Maven + JDK
FROM maven:3.9.6-eclipse-temurin-17

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Build project, bỏ qua test
RUN mvn clean package -DskipTests

# Chạy ứng dụng Spring Boot (chú ý sửa tên file jar nếu khác)
CMD ["java", "-jar", "target/holyTask_be-0.0.1-SNAPSHOT.jar"]
