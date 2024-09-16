#!/bin/bash

KAFKA_DIR="~/kafka_2.13-3.8.0/bin"
MESSAGES_FILE="./messages.json"

cat $MESSAGES_FILE | bash -c "$KAFKA_DIR/kafka-console-producer.sh --broker-list localhost:9092 --topic books"