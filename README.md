# api-gateway

Kotlin powered minimalistic API Gateway - a playground to test-drive Kotlin's co-routines.

## Building

```shell
gradle clean build --parallel
```

## Testing

```shell
gradle clean test --parallel
```

## Dockerize

```shell
gradle clean installDist --parallel
docker build -t api-gateway -f ./service/Dockerfile ./service
```

## Running Locally

```shell
docker run -p 8080:8080 api-gateway:latest
```
