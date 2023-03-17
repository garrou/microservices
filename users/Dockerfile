FROM maven

COPY . /app

WORKDIR /app

RUN mvn clean compile install -DskipTests

CMD mvn spring-boot:run