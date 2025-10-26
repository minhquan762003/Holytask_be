# Sử dụng JDK 23
FROM eclipse-temurin:23-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy file Maven wrapper và cấu hình
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy toàn bộ project và build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Chạy ứng dụng
EXPOSE 8080
CMD ["java", "-jar", "target/holyTask_be-0.0.1-SNAPSHOT.jar"]
