FROM openjdk:19-alpine
ADD out/artifacts/DockerVersion_jar/DockerVersion.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]