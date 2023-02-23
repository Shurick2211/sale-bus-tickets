#!/bin/bash

curl -X'POST' 'http://localhost:8080/payments' -H 'Content-Type: application/json' -d '{"full_name": "Василенко Петр Дмитрович", "sum": 15.05}'
echo -e

curl -X'GET' 'http://localhost:8080/payments?id=12wer4'
echo -e

curl -X 'POST' 'http://localhost:8080/tickets' -H 'accept: */*' -H 'Content-Type: application/json'  -d '{"full_name": "Петров Петр Птрович","flight_id": "1A"}'
echo -e

curl -X 'GET' 'http://localhost:8080/tickets/1' -H 'accept: */*'
echo -e
