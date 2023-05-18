FROM maven

COPY . /app

WORKDIR /app

RUN mvn clean install -DskipTests

CMD mvn spring-boot:run