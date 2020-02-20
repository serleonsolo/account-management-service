FROM jboss/base-jdk:8
MAINTAINER serleonsolo

ADD account-service-application/target/account-service-application-1.0-SNAPSHOT.jar /app/account-service-application.jar
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "/app/account-service-application.jar"]
