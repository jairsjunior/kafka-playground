#!/bin/sh

#Check if server is up
while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' hydra:4444)" != "404" ]]; 
do sleep 5; 
done

#Create users for kafka-broker
hydra clients import /client-broker.json /client-consumer.json /client-producer.json --endpoint http://hydra:4445