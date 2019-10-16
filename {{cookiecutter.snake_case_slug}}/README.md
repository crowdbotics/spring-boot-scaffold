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
{% if cookiecutter.spring_boot_authentication == "ldap" %}
    - spring-ldap-core
    - spring-security-ldap
{% endif %}
{% if cookiecutter.spring_boot_authentication == "oauth2" %}
    - spring-security-oauth2-client
    - spring-security-oauth2-jose
{% endif %}
{% if cookiecutter.has_spring_boot_devtools == "y" %}
  - spring-boot-devtools
{% endif %}
{% if cookiecutter.has_lombok == "y" %}
  - lombok
{% endif %}
{% if cookiecutter.has_spring_boot_rest == "y" %}
  - spring-boot-starter-data-rest
{% endif %}
{% if cookiecutter.has_spring_boot_session == "y" %}
  - spring-session-data-redis
{% endif %}
{% if cookiecutter.database_option == "h2" %}
  - h2
{% endif %}
{% if cookiecutter.database_option == "mongodb" %}
{% endif %}
{% if cookiecutter.database_option == "mysql" %}
    - mysql-connector-java
{% endif %}
{% if cookiecutter.database_option == "postgresql" %}
    - postgresql
{% endif %}
{% if cookiecutter.has_spring_boot_jms == "y" %}
  - spring-boot-starter-activemq
  - activemq-broker
{% endif %}
