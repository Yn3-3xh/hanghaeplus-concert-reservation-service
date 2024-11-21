#!/bin/bash

# Kafka 토픽 생성
echo "Creating topic"
kafka-topics --create --topic payment-alarm --partitions 1 --replication-factor 1 --if-not-exists --bootstrap-server kafka:9092

# 생성된 토픽 설명 출력
echo "Describing topic"
kafka-topics --describe --topic payment-alarm --bootstrap-server kafka:9092

# 콘솔 프로듀서를 통해 메시지 전송
echo "Sending a message"
echo "Hello, Kafka!" | kafka-console-producer --topic payment-alarm --broker-list kafka:9092

# 콘솔 소비자 시작 (첫 번째로 메시지를 읽을 수 있도록 설정)
echo "Consuming messages"
kafka-console-consumer --topic payment-alarm --bootstrap-server kafka:9092 --from-beginning
