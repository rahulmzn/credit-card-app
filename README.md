# Credit Card App
Credit card application allows adding new credit card and fetch existing credit cards from system over Restful API's.

<!-- TABLE OF CONTENTS -->
## Table of Contents

<details open="open">
   <ul>
      <li>
          <a href="#requirement">Requirement</a>
      </li>
      <li>
         <a href="#technology-stack-&-other-open---source-libraries">Technology stack &amp; other Open-source libraries</a>
         <ul>
            <li><a href="#database">Database</a></li>
            <li><a href="#server---backend">Server - Backend</a></li>
            <li><a href="#libraries-and-plugins">Libraries and Plugins</a></li>
            <li><a href="#others">Others</a></li>
         </ul>
      </li>
      <li>
         <a href="#getting-started">Getting Started</a>
         <ul>
            <li><a href="#running-credit-card-app-with-maven">Running Credit Card App with Maven</a>
                <ul>
                <li><a href="#on-mac-os">Run on Mac OS</a></li>
                <li><a href="#on-windows-os">Run on Windows OS</a></li>
                </ul>
            </li>
            <li><a href="#creating-executable-jar-and-then-running-the-application">Creating executable JAR and then running the application</a></li>
            <li><a href="#accessing-data-in-h2-database">Accessing Data in H2 Database</a>
                 <ul><li><a href="#h2-console">H2 Console</a></li></ul>
            </li>
         </ul>
      </li>
      <li>
         <a href="#code-coverage">Test Report</a>
         <ul>
            <li><a href="#jacoco">Jacoco</a></li>
         </ul>
      </li>
      <li>
         <a href="#testing-api">Testing API</a>
         <ul>
            <li><a href="#testing-with-postman-runner">Testing with Postman Runner</a></li>
            <li><a href="#testing-with-maven">Testing with Maven</a></li>
         </ul>
      </li>
      <li>
          <a href="#using-application">Using Application</a>
      </li>
      <li><a href="#documentation">Documentation</a></li>
      <li><a href="#contact">Contact</a></li>
   </ul>
</details>

## Requirement
System should allow adding new credit card and fetching all existing cards from database over Restful API.
* Validate credit card number using Luhn10
* New credit card start with £0
* For cards not compatible with Luhn 10, return an error

## About Solution
The credit card app has been built upon Spring Boot with support of Embedded H2 DB, Spring REST Docs and Swagger to provide quick access on resources.

This application provides below endpoints
```    
[POST] /api/v1/cards   : Add new credit card
[GET] /api/v1/cards    : Fetch existing cards
```
* Below validation implementation has been done for credit card number and Object properties 
    * Credit card number will be validated with custom written validation using LUHN-10 Algorithm.
    * With **Card Number** custom annotation so that it can be reused later.
    * Basic validation used from provided by libraries like NotNull etc. ( To prove less code & more config)

> **_NOTE:_** Credit card number will be validated by custom Luhn10 implementation which will be handle by our own created custom validation annotation applied on card->number field .
    
## Technology stack & other Open-source libraries 

### Database
* [H2 Database Engine](https://www.h2database.com/html/main.html) Java SQL database. Embedded and server modes; in-memory databases
      
### Server-Backend

<details open="open">
   <ul>
      <li><a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK</a> - Java™ Platform, Standard Edition Development Kit</li>
      <li><a href="https://spring.io/projects/spring-boot">Spring Boot</a> - Framework to ease the bootstrapping and development of new Spring Applications</li>
      <li><a href="https://maven.apache.org/">Maven</a> - Dependency Management</li>
   </ul>
</details>

### Libraries and Plugins

<details open="open">
   <ul>
      <li><a href="https://swagger.io/">Swagger</a> - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.</li>
   </ul>
</details>

### Others

<details open="open">
   <ul>
      <li><a href="https://git-scm.com/">Git</a> - Free and Open-Source distributed version control system</li>
   </ul>
</details>

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes.

## Prerequisites to Build & Run Credit Card App
This application can be test based on availability of below tools & software.	 

#### Essential
    Java version 8
    
#### Optional
    Development IDE
    REST API Testing Tools (like postman)
    Maven version 3 or later (Good to have)

### Download source code from GitHub

 Source code of credit card application can be download [from here](https://github.com/rahulmzn/credit-card-app/)

#### Download as Zip 

Zip file of full source code can be downloaded [from here](https://github.com/rahulmzn/credit-card-app/archive/refs/heads/master.zip)

#### Download as source repository 
    
    git clone https://github.com/rahulmzn/credit-card-app.git


#### Application properties Config
See [application.properties](https://github.com/rahulmzn/credit-card-app/blob/master/src/main/resources/application.properties) 


### Installing App

#### Running credit card app with maven
>_NOTE_: To run any of below commands system command prompt should point inside downloaded application folder

        e.g cd credit-card-app

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so (Maven should be installed in the system and mvn command is accessible):

Application will be by default published on port 8085 at localhost, 
>_NOTE:_ This also can be customised by changing port value for server.port property [application.properties](https://github.com/rahulmzn/credit-card-app/blob/master/src/main/resources/application.properties).
>Once port has been changed, do update below endpoint on newly configured port

##### On mac os
```shell
$ git clone https://github.com/rahulmzn/credit-card-app.git
$ cd credit-card-app
$ ./mvnw spring-boot:run
```
##### On windows os
```shell
$ git clone https://github.com/rahulmzn/credit-card-app.git
$ cd credit-card-app
$ ./mvnw.bat spring-boot:run
```

#### Creating executable JAR and then running the application

The code can also be built into a jar and then executed/run. Once the jar is built, run the jar by double-clicking on it
or by using the command.

```shell
$ git clone https://github.com/rahulmzn/credit-card-app.git
$ cd credit-card-app
$ mvn package -DskipTests
$ java -jar target/credit-card-app-0.0.1-SNAPSHOT.jar.jar
```
## Testing API
>_NOTE:_ All below links will only be available after running app with above steps
#### Testing with Swagger
* [Swagger](http://localhost:8085/swagger-ui/) - http://localhost:8085/swagger-ui  

#### Accessing Credit Card API from command prompt (terminal) using CURL commands
After deployment of application [Test Restful API](http://localhost:8085/swagger-ui-custom.html) link can be followed to test API's. 
#### Add new card
     : curl --location --request POST 'http://localhost:8085/api/v1/cards' --header 'Content-Type: application/json' --data-raw '{ "brand": "VISA", "type": "testing type", "currency": "GBP", "limit": "1000", "funding": "", "number": "4012888888881881", "country": "GB", "name": "Rahul Kumr" }'
#### Fetch All cards
     : curl --header 'Content-Type: application/json' 'http://localhost:8085/api/v1/cards'

#### Testing with Postman Runner
Import the `docs\Credit-Card-App.postman_collection.json` file into postman and run the API tests (Spring Boot service should be running).

#### Testing with Junit Maven
* Run only unit tests:

```shell
$ ./mvn clean test
```


To shut down the jar, follow the below mentioned steps on a Mac os machine.

* In command prompt execute the **lsof -i tcp:8085** command to print a list of all running Java processes
* **kill -9 /PID PROCESS_ID_OF_RUNNING_APP /F** execute this command by replacing the **PROCESS_ID_OF_RUNNING_APP**
  with the actual process id of the running jar found out from executing the previous command
* Press enter


To shut down the jar, follow the below mentioned steps on a Windows machine.

* In command prompt execute the **jcmd** command to print a list of all running Java processes
* **Taskkill /PID PROCESS_ID_OF_RUNNING_APP /F** execute this command by replacing the **PROCESS_ID_OF_RUNNING_APP**
  with the actual process id of the running jar found out from executing the previous command
* Press Ctrl+C
### Accessing Data in H2 Database

##### H2 Console

URL to access H2 console: **http://localhost:8085/h2-console/login.jsp**.

Fill the login form as follows and click on Connect:

* Saved Settings: **Generic H2 (Embedded)**
* Setting Name: **Generic H2 (Embedded)**
* Driver class: **org.h2.Driver**
* JDBC URL: **jdbc:h2:mem:bank;DB_CLOSE_ON_EXIT=FALSE**
* User Name: **sa**
* Password:

## Executed Test Reports
### Jacoco

Generating code coverage reports

##### On mac os
```shell
$ git clone https://github.com/rahulmzn/credit-card-app.git
$ cd credit-card-app
$ ./mvnw clean test jacoco:report

```
##### On windows os
```shell
$ git clone https://github.com/rahulmzn/credit-card-app.git
$ cd credit-card-app
$ ./mvnw.bat clean test jacoco:report
```
This will create a detailed HTML style report showing code coverage statistics gathered via code instrumentation.

**credit-card-app\target\site\jacoco\index.html**

### Documentation
Credit card processing application provides below types of documentation 	
* Java Docs added with source code.
* Swagger API specification documentation can we access as below
    * [Swagger Api Docs as Json](http://localhost:8085/v2/api-docs)

## Contact
Project Link: [CreditCardApp](https://github.com/rahulmzn/credit-card-app)
