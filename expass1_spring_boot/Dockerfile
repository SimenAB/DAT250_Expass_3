#build with correct gradle -v
FROM gradle:8.14.3-jdk21 AS builder
WORKDIR /pollapp

#Copy build and wrapper
COPY settings.gradle.kts build.gradle.kts /pollapp/
COPY gradlew /pollapp/
COPY gradle /pollapp/gradle

COPY src /pollapp/src

#build spring boot jar
RUN ./gradlew clean bootJar --no-daemon

#run the application - keep it slim
FROM eclipse-temurin:21-jre-slim AS runner
WORKDIR /pollapp

#create non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

#copy built jar
COPY --from=builder /pollapp/builder/libs/*.jar /app/app.jar

#Spring Boot port
EXPOSE 8080

#start app - final instruction
CMD ["java", "-jar", "/app/app.jar"]

