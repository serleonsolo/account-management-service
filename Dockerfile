FROM fabric8/java-jboss-openjdk8-jdk:1.2.3
MAINTAINER serleonsolo

ADD account-service-application/target/account-service-application-1.0-SNAPSHOT.jar /app/account-service-application.jar
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "/app/account-service-application.jar"]
