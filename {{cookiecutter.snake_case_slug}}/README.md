# spring-boot-scaffold
The Crowdbotics Spring Boot with React scaffold

## Running the app

### 1. Start the backend
```
$ cd backend
$ mvn package
$ mvn spring-boot:run
```

### 2. Start the frontend
```
$ cd frontend
$ npm install
$ npm start
```

The terminal should automatically open a new browser window and navigate to `localhost:3000`. You can also do this manually.

### Included libraries based on options
- spring_boot_authentication
  - ldap
    - spring-ldap-core
    - spring-security-ldap
  - oauth2
    - spring-security-oauth2-client
    - spring-security-oauth2-jose
- has_spring_boot_devtools
  - spring-boot-devtools
- has_lombok
  - lombok
- has_spring_boot_rest
  - spring-boot-starter-data-rest
- has_spring_boot_session
  - spring-session-data-redis
- database_option
  - h2
    - h2
  - mongodb
  - mysql
    - mysql-connector-java
  - postgresql
    - postgresql
- has_spring_boot_jms
  - spring-boot-starter-activemq
  - activemq-broker
