FROM openjdk:11-jdk
EXPOSE 8080:8080
RUN mkdir /service
COPY ./build/install/service/ /service/
WORKDIR /service/bin
CMD ["./service"]
