#!/bin/sh

#Check if server is up

while [[ "$(nc -z kafka 9092 </dev/null; echo $?)" !=  "0" ]]; 
do sleep 5;
echo "Waiting for KAFKA SERVER is UP and RESPONDING"; 
done

sleep 5;

npm start