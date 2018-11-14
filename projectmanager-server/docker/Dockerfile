FROM openjdk:8
EXPOSE 8085
ADD projectmanager-server-0.0.1-SNAPSHOT.jar projectmanager-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "projectmanager-server-0.0.1-SNAPSHOT.jar"]
RUN sh -c 'touch /projectmanager-server-0.0.1-SNAPSHOT.jar'