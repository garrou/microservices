# microservices

[![docker compose build](https://github.com/garrou/microservices/actions/workflows/docker-compose-build.yml/badge.svg)](https://github.com/garrou/microservices/actions/workflows/docker-compose-build.yml)

[![maven test](https://github.com/garrou/microservices/actions/workflows/maven-test.yml/badge.svg)](https://github.com/garrou/microservices/actions/workflows/maven-test.yml)

Micro services architecture

## Features

- Users (Postgres)
- Courses (Mongo)
- Competitions (Mongo)
- Statistics
- Badges (Mongo)
- Participations (Postgres)

## Docker

```sh
docker-compose up -d
```

## Endpoints

- Gateway : http://localhost:8080
- Prometheus : http://localhost:9090
- Eureka : http://localhost:9000
- Zipkin : http://localhost:9411/zipkin