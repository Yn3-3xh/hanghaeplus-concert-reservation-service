#!/bin/bash

# Kafka가 시작되었는지 확인하는 함수
wait_for_kafka() {
  while ! nc -z kafka 9092; do
    echo "Waiting for Kafka to be ready..."
    sleep 1
  done
  echo "Kafka is ready!"
}

# Kafka가 시작되기를 기다림
wait_for_kafka