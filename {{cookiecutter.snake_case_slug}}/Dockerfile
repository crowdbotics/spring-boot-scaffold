# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

RUN apt-get install -y --no-install-recommends nodejs

ADD ./backend /app
WORKDIR /app

# Run Maven build
RUN mvn clean install

ADD ./frontend /frontend
WORKDIR /frontend

RUN npm install
RUN npm run build
COPY build /app/src/main/webapp/WEB-INF/view/react


# Just using the build artifact and then removing the build-container
FROM openjdk:11-jdk

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/app/target/sprint_deploy_test1__1031-*-SNAPSHOT.jar" app.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java -Dserver.port=$PORT -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
