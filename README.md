# How to setup OAuth2 mechanism to Kafka

With the commit of KIP-255 (Kafka Improvement Proposal) at version 2.0.0 of Kafka, now we can use SASL (Simple Authentication and Security Layer) OAUTHBEARER to authenticate clients to the broker or interbroker authentication.

## Prerequisites
- Docker
- Docker-compose
- Git

## Implementation of Java Classes to support OAuth mechanism

With the documentation of KIP-255 in hands, we need to implement 2 classes to use an external OAuth server to autenticate our clients or brokers.
The first class implements AuthenticateCallbackHandler and will serve for clients or broker that needs to authenthicate.
The second class implements the same class and will serve to broker can make the validation of the sended token using OAuth token instrospection.
We will implement two another helper classes, one for handle our http requests (Get Token and Introspect Token) and a class to set our JWT properties.

## Configuring Kafka Broker

For this configuration, we need to do three steps:
- Create an JAAS configuration file setting the login module that we will use
- Add some properties to server.properties file
- Add our jar file to kafka lib folder.

## Starting an OAuth2 Server and our Kafka Broker

This project requires an OAuth2 server to provide the token and validation of our client or broker. An easy and opensource alternative is use ORY Hydra, that is an Certified OAuth2 server written in Go. 
For this example, we use a docker-compose file that setup server and create 3 accounts:
- consumer-kafka: for consumer container
- producer-kafka: for producer container
- broker-kafka: for interbroker authentication

The below commands will run:
- Zookeeper
- Kafka Broker
- ORY Hydra (OAuth2 Server)
- Producer
- Consumer

### Run Kafka using Confluent official docker images

At the folder `using-confluent-image` run the command:

```
docker-compose up
```

### Run Kafka using Wurstmeister images

At the folder `using-wurstmeister-image` run the command:

```
docker-compose up
```

## Examples Folder

This folder contains one example of producer and consumer using the below languages.
- Java
- Javascript
