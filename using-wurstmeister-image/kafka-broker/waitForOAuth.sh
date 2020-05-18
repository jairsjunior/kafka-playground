#!/bin/sh

#Check if server is up
while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' $OAUTH_LOGIN_SERVER)" != "404" ]]; 
do sleep 5;
echo "Waiting for OAUTH LOGIN SERVER $OAUTH_LOGIN_SERVER is UP and RESPONDING"; 
done

sleep 10;

start-kafka.sh