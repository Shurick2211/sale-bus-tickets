#!/bin/bash

curl -X'POST' 'http://localhost:5000/payments' -H 'Content-Type: application/json' -d '{"full_name": "Василенко Петр Дмитрович", "sum": 15.05}'
echo -e

curl -X'GET' 'http://localhost:5000/payments?id=1'
echo -e

curl -X 'POST' 'http://localhost:5000/tickets' -H 'accept: */*' -H 'Content-Type: application/json'  -d '{"full_name": "Петров Петр Птрович","flight_id": "1A"}'
echo -e

curl -X 'GET' 'http://localhost:5000/tickets/2' -H 'accept: */*'
echo -e
