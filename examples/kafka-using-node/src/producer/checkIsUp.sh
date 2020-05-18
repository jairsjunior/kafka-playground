#!/bin/sh

PORT="${KAFKA_HOST#*:}"
HOST="${KAFKA_HOST%:*}"

#Check if server is up
while [[ "$(nc -z $HOST $PORT </dev/null; echo $?)" !=  "0" ]]; 
do sleep 5;
echo "Waiting for KAFKA SERVER is UP and RESPONDING"; 
done

sleep 5;

npm start