FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/Timesheet-spring-boot-2.0.war Timesheet-spring-boot.war
ENTRYPOINT ["java","-jar","/Timesheet-spring-boot.war"]
