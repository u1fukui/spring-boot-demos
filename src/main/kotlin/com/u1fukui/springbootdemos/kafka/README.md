# Spring for Apache Kafka

## 概要
- [Spring Boot auto-configuration for Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html#boot-features-kafka)
- [Spring for Apache Kafka](https://docs.spring.io/spring-kafka/reference/html/)
- [Intro to Apache Kafka with Spring | Baeldung](https://www.baeldung.com/spring-kafka)


## Kafka の準備

### 1. Kafka をインストールする
`$ brew install kafka`

### 2. zookeeper を起動する
`$ brew services start zookeeper`

### 3. Kafka を起動する
`$ brew services start kafka`

### 4. Topic を作成する
```
# Topic 作成
$ kafka-topics --zookeeper localhost:2181 --create --partitions 2 --topic test --replication-factor 1
Created topic "test".

# Topic 一覧確認
$ kafka-topics --list --zookeeper localhost:2181
__consumer_offsets
test

# Topic 詳細確認
$ kafka-topics --describe --zookeeper localhost:2181 --topic test
Topic:test	PartitionCount:2	ReplicationFactor:1	Configs:
	Topic: test	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: test	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
```

## Spring for Apache Kafka のデモ作成

### 1. spring-kafka を使う。
`implementation 'org.springframework.kafka:spring-kafka'`

### 2. application.yml に Redis の設定を追加
Brokerの接続先を書く。ここで設定する名前は何でもよい
```
kafka:
  bootstrapServers: localhost:9092 
```

### 3-1. Producer(メッセージ送信)の作成
- Brokerの接続先、メッセージのkey/valueのSerializerを指定して、ProducerFactoryを作成
- ProducerFactoryを使用して、KafkaTemplateを作成
- KafkaTemplateを使用して、メッセージを送信する

### 3-2. Consumer(メッセージ受信) の作成
- 今回のデモでは、 `@KafkaListener` アノテーションを利用
- Brokerの接続先、メッセージのkey/valueのSerializerを指定して、ConsumerFactoryを作成
- ConsumerFactoryを使用して、ConcurrentKafkaListenerContainerFactoryを作成
- メッセージのハンドリング処理をするメソッドに、`@KafkaListener`アノテーションを付ける