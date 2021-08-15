From openjdk:8
MAINTAINER ravipece@gmail.com
ADD build/libs/shorten-url-api-1.0.1-SNAPSHOT.jar url-shortener.jar
EXPOSE 8080
CMD ["java","-jar","url-shortener.jar"]