# spring-boot-scaffold
The Crowdbotics Spring Boot with React scaffold

## Running the app - {{cookiecutter.project_slug}}

### 1. Build {{cookiecutter.project_slug}}
```
mvn clean package
```
### 2. Start {{cookiecutter.project_slug}}
```
$ cd backend
$ mvn spring-boot:run
```


The terminal should automatically open a new browser window and navigate to `localhost:8080/`. You can also do this manually.

### Included Scaffold-Time Options
{% if cookiecutter.spring_boot_authentication == "jwt" %}
    - java-jwt
{% endif %}
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
