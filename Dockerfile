# Sử dụng image OpenJDK để chạy ứng dụng
FROM eclipse-temurin:17-jre-jammy AS final

# Tạo thư mục làm việc
WORKDIR /app

# Sao chép tệp JAR đã được xây dựng vào thư mục làm việc
COPY target/globie-0.0.1-SNAPSHOT.jar /app/app.jar

# Mở cổng mà ứng dụng sẽ lắng nghe
EXPOSE 8080

# Thiết lập lệnh khởi động ứng dụng
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
