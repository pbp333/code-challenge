FROM openjdk:11.0.6-jre-slim
MAINTAINER pbp333
ADD target/commit-viewer-0.0.1-SNAPSHOT.war commit-viewer-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "commit-viewer-0.0.1-SNAPSHOT.war"]