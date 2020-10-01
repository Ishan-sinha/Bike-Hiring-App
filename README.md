# Bike Rental Application
[![Build Status](https://travis-ci.com/adityagupta1089/Bike-Rental-App.svg?token=4v3vJQcDSzzVad8yn6JN&branch=master)](https://travis-ci.com/adityagupta1089/Bike-Rental-App)
[![Maintainability](https://api.codeclimate.com/v1/badges/7eafcae08a876b764447/maintainability)](https://codeclimate.com/github/adityagupta1089/Bike-Rental-App/maintainability)
[![codecov](https://codecov.io/gh/adityagupta1089/Bike-Rental-App/branch/master/graph/badge.svg)](https://codecov.io/gh/adityagupta1089/Bike-Rental-App)
[![Known Vulnerabilities](https://snyk.io/test/github/adityagupta1089/Bike-Rental-App/badge.svg)](https://snyk.io/test/github/adityagupta1089/Bike-Rental-App) 

## 1. Introduction 
This is the server component of an application that is used for renting or leasing cycles. 
## Changes
New Changes include better optimization of server, easy and faster navigation, faster loadability of the server and parallel operation and execution.

## 2. Requirements
- [Maven](https://maven.apache.org/)
- [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

## 3. Getting started
1. Open terminal
2. `mvn dependency:resolve`
3. `mvn clean compile install`
4. `mvn eclipse:eclipse -DdownloadSources=true`
5. From eclipse, File –> Import –> Existing Project into workspace
6. Create initial Database Tables: `java -jar target/bike-rental-app-0.1.jar db migrate dev.yml`
7. Can be run using `com.csl456.BikeRentalApplication server dev.yml` or
```
java -jar bike-rental-app-0.1.jar server dev.yml
```

## 4. Miscellaneous:

### 4.1. Database Operations:

#### 4.1.1. Check if database state matches:
```
java -jar bike-rental-app-0.1.jar db status dev.yml
```

#### 4.1.2. Prepare rollback for migration (before applying migrations):
```
java -jar bike-rental-app-0.1.jar db prepare-rollback dev.yml
```

#### 4.1.3. Migrate database changes:
Please use `--dry-run` first

```
java -jar bike-rental-app-0.1.jar db migrate dev.yml
```

