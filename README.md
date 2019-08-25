# spring-boot-demos

## Redis Demo
- このデモを行う前に、Redis を起動する必要がある
- `curl -X POST localhost:8080/redis/count-up`
  - API呼び出しの度に、Redisに保存してある値を +1 する
- `curl -X POST localhost:8080/redis/clear`
  - count-up API で参照している Redis の値をクリアする
- `curl localhost:8080/spring-cache/search?q=android`
  - `q`に指定した値で GitHub のリポジトリを検索した結果を返す
  - 検索結果は、RedisTemplate を使って Redis に保存する
  - 過去の検索結果がRedisにある場合は、GitHub から検索せずに、キャッシュを返す


## Spring Cache Demo
- このデモを行う前に、Redis を起動する必要がある
- `curl -X POST localhost:8080/spring-cache/search`
  - `q`に指定した値で GitHub のリポジトリを検索した結果を返す
  - 検索結果は、@Cacheable アノテーションを使って Redis に保存する
  - 過去の検索結果がRedisにある場合は、GitHub から検索せずに、キャッシュを返す


## Kafka Demo
- このデモを行う前に、 Kafka を起動する必要がある
- `curl -X POST localhost:8080/kafka/message?message=hello`
  - `message`に指定した値を、Kafka の `test` topic に送る