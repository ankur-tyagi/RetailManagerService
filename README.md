# RetailManager
This is retail manager rest service, which provide 2 API to expose 

API end point : api/shops [GET]
API Details : Get all nearby shops, using longitude and latitude.

API end point : api/shops [POST]
API Details : Add shop to retail manager service.


# How to build and execute using gradle
gradle command to build : "gradle build"
gradle command to start service : "java -jar ./build/lilbs/RetailService-0.1.0.jar"


# Test using postman collection 
Use 'RetailManagerService.postman_collection.json' postman collection, available in github.
During testing using postman, service connects to Google Geocoding service; this needs internet connection.


# Build Reports 
Test reports are generated under build/reports/. This has report of unit-tests executed during build.


# Technical designs 
* This uses spring-boot to build rest service in MVC pattern. Rest Controller interacts with external user and business service.
* Business Service implements logic in terms of domain. We have separate REST client class class which interacts with external Google Geocoding API.
* Data layer(repository service) is implemented as component as we have basic operations, for now. This only supports basic minimal functionality.


# Production Ready
We list technical items to taken care for production ready
* Need to use profile based configuration(Dev/QA/Production), which will help configure information like Google API Key and database configurations.
* To handle huge data, pagination will be required for search API.
* Addition of Code Coverage, PMD reports.
* Transactional semantics, for operation to be complaint with commit/rollback/failover. This will help maintain data integrity.
* Caching : This require to handle based on MRU(most recently used) data should be cache as 20% of data reused 80% of time, so if we cache data, we don't require to fetch data from down layer.
* Authentication can be added atleast for API like Add Shop, to avoid adding garbage data to system.
* Monitoring: This is building matrix which will provide insights like performance of system, usages of system, business value of system..
* Error Handling needs to be implemented to handle real scenarios and provide logical response to API users.
* Capacity and Performance : workout on business case about call per day and service response time.
* Unit testing and functional testing: This should cover all kind scenarios to handle error and exceptions.	


# TasksList
* use actual database
* addition of code coverage
* accutator for monitoring of service
* update comments and documentaion. 
* add REST APIs for administration of service
