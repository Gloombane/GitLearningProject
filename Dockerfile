FROM eclipse-temurin:25-jdk
WORKDIR /backend
COPY target/GitLearningProjectWithSpringAndReact-0.0.1-SNAPSHOT.jar  firstDockerBack.jar
ENTRYPOINT ["java", "-jar", "firstDockerBack.jar"]
