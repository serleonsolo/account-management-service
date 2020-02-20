#!/usr/bin/env bash
mvn clean install
cd account-service-application
mvn spring-boot:run -Dspring.profiles.active=development
