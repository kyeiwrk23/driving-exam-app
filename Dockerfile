FROM openjdk:22-jdk
ADD  target/driver-test.jar driver-test.jar
ENTRYPOINT ["java","-jar","/driver-test.jar"]