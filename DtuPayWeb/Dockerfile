FROM maven:latest

WORKDIR /usr/src
COPY . /usr/src
EXPOSE 8080 80

RUN mvn clean package
CMD java -Xmx64m -ea -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -jar target/DtuPayWeb-thorntail.jar
