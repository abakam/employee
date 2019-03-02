
# Tools and Technologies Used
  * Spring MVC - 5.1.0 RELEASE
  * Hibernate - 5.3.5.Final
  * JDK - 11
  * Maven - 3.7.0
  * Apache Tomcat - 7.0.92
  * IDE - IntelliJ IDEA (ULTIMATE 2018.3)
  * Apache Derby - 10.13.1.1
  * JUnit - 4.12
  * Mockito-core - 1.10.19
  * Postman

# Setup and Test
  * Clone this repo to your local machine
  * Open IntelliJ IDEA
  * Import this project
  * Expand the Maven Menu bar on the left
  
  ![myimage-alt-tag](https://cdn-images-1.medium.com/max/1600/1*U_PdPYok-XUT8TJNnETHow.png)
  
  * On Maven Toolbar, click the cyclic icon highlighted in the image above to update dependencies
  * Expand the Lifecycle submenu
  * Select clean
  * Click play icon on Maven Tool bar to run maven clean - to clean the project
  * Select package
  * Click play icon on Maven Tool bar to run maven package - to create a war file
  * Expand the Plugins submenu
  * Expand tomcat7 submenu
  * select tomcat7:run-war
  * Click play icon on Maven Tool bar to run the project on tomcat7 server

# Testing API 
  After a successful build and run, the app should run on http://localhost:5000 -- baseURL. The port can be updated in the pom.xml file within tomcat configuration. Using Postman or any RESTful API testing tool, the following endpoints can be tested: 
  ## baseURL/api/v1/employees
  * GET returns all saved records
  * POST takes employee record and save to database. Sample payload:
    ```
    {
      "name": "John Doe",
      "age": 50,
      "gender": "MALE",
      "email": "johndoe@example.com",
      "salary": 1000000
    }
    ```
  * PUT takes employee record with id and update the database if there are changes. Sample payload:
    ```
    {
      "id": 1,
      "name": "John Doe",
      "age": 50,
      "gender": "MALE",
      "email": "johndoe@example.com",
      "salary": 1000000
    }
    ```
  
