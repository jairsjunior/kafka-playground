# How to setup OAuth2 mechanism to a Kafka Cluster

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

For start our OAuth2 server and our Kafka broker we need to clone this repo to run the docker-compose file at root folder. Before running docker-compose we need to set an environment variable called HOST_IP wich contains IP address of running machine.

```
HOST_IP=XXX.XXX.XXX.XXX docker-compose up
```

## Configure our clients (Producer/Consumer) to use this mechanism

For now we can use our jar file to configure clients using Java or Scala. For javascript developer there is an option, kafka-node-oauth npm module that implements this mechanism.

### Java/Scala

At the cloned repo, we have a folder called kafka-using-java, that contains one producer example, using our .jar file. To run this example you will need to set HOST_IP environment variable wich contains IP address of running machine.

```
HOST_IP=XXX.XXX.XXX.XXX docker-compose up
```

### Javascript

At the cloned repo, we have a folder called kafka-using-node, that contains one producer and one consumer example, using our modified kafka-node-oauth package. To run this example you will need to set HOST_IP environment variable wich contains IP address of running machine.

```
HOST_IP=XXX.XXX.XXX.XXX docker-compose up
```