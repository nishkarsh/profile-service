# ProfileService [A Demo Service]
A Java Spring Boot based service that offers APIs to be used by PeopleApp and other clients belonging to the same domain.

The service offers Restful APIs to CREATE, UPDATE and FETCH user profile.

### Requirements
- MongoDB 3.6
- Internet for the dependencies to be downloaded by gradle

### Steps to boot up service
- Clone the repository on your local machine
- Use command ```./gradlew clean build``` to build the application
- Use command ```./gradlew bootRun``` to boot up the service

The service will bootup on port 9740. Check if the service is up by visiting http://localhost:9740/health-check

#### Please make sure the mongodb server is running

### Technologies Used
- Java 11
- Spring Boot
- Jackson as ObjectMapper
- Junit 5 for Unit Testing
