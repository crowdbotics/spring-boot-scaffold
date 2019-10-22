# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

# Docker image is based on Debian Stretch which has extremely out-of-date packages
# Backports is also out of date but much better
RUN echo "deb http://deb.debian.org/debian stretch-backports main" > /etc/apt/sources.list.d/backports.list
RUN apt-get update && apt-get install -y --no-install-recommends -t stretch-backports \
        nodejs \
        npm \
    && rm -rf /var/lib/apt/lists/*

ADD ./backend /app
ADD ./frontend /frontend

WORKDIR /frontend

RUN npm install
RUN npm run build && cp -r build /app/public

WORKDIR /app

# Run Maven build
RUN mvn clean install


# Just using the build artifact and then removing the build-container
FROM openjdk:11-jdk

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/app/target/{{cookiecutter.snake_case_slug}}-*-SNAPSHOT.jar" app.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java -Dserver.port=$PORT -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
