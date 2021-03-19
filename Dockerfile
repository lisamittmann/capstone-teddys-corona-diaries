FROM openjdk:15

MAINTAINER Lisa <teddyskitchenadventures@gmail.com>

ADD backend/target/teddys-corona-diaries.jar app.jar

CMD ["sh", "-c", "java -jar /app.jar"]