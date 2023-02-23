#!/bin/bash
var1=10
while [ $var1 -gt 0 ]
do
curl -X 'POST' 'http://localhost:8080/tickets' -H 'accept: */*' -H 'Content-Type: application/json'  -d '{"full_name": "Петров Петр Птрович","flight_id": "1A"}'
echo -e
var1=$[ $var1 - 1 ]
done
