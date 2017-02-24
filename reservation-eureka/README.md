# Eureka service
This service is used for service discovery

## Setup
The configuration server has a configuration for the eureka server: eureka-service.properties
```
server.port=8761
eureka.client.register-with-eureka: false
eureka.client.fetch-registry: false
```