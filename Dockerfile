FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew bootJar
CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar build/libs/*.jar"]
