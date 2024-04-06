FROM openjdk:20-jdk

EXPOSE 5500

ADD target/TransferService-0.0.1-SNAPSHOT.jar transfer_app.jar

ENTRYPOINT ["java", "-jar", "/transfer_app.jar"]