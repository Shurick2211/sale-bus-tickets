#!/bin/bash

curl -X'POST' 'http://localhost:8080/payments' -H 'Content-Type: application/json' -d '{"fullName": "Василенко Петр Дмитрович", "sum": 15.05}'
echo -e

curl -X'GET' 'http://localhost:8080/payments?id=12wer4'
echo -e
