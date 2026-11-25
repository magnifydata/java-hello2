FROM gradle:jdk-lts-and-current
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon
CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar build/libs/*.jar"]
