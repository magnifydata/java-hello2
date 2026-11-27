FROM gradle:jdk17
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon
ENV JAVA_TOOL_OPTIONS="-Xmx350m -Xms128m"
CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar build/libs/*.jar"]
