FROM maven

COPY . /app

WORKDIR /app

RUN mvn clean install

CMD mvn spring-boot:run