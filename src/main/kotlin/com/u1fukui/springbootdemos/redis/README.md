# Spring Data Redis

## 概要
- https://spring.io/projects/spring-data-redis
- https://docs.spring.io/spring-data/redis/docs/current/reference/html/
- TODO: spring boot 用の参考になる開発ガイドを探す


## 実際に行った手順
### 1. spring-boot-starter-data-redis を使う。
`implementation 'org.springframework.boot:spring-boot-starter-data-redis'`

### 2. application.yml に Redis の設定を追加
```
spring.redis:
  host: localhost
  port: 6379
  password: null
  database: 0
```

`spring.redis` で検索すれば、redis の設定項目が確認できる
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties

### 3. RedisTemplateを使ってコードからRedisを利用する
- `application.yml` に書いた redis server を利用する `RedisTemplate` の Bean が提供されているので、`@Autowired` などを使って変数に inject して使う。
- String型の値を格納する場合は `StringRedisTemplate` を使う等、格納する型によっていくつかクラスが用意されている


## Objectを保存する際に気をつけること
実際にRedisに保存する際に、Objectをどのような形式で保存するか。

### JdkSerializationRedisSerializer
- ObjectをJava標準のシリアライズ処理をした文字列で保存する
- デフォルトのSerialize方法
- 保存した後にObjectのプロパティに変更があると、取り出す時(デシリアライズ時)にエラーになってしまう

### Jackson2JsonRedisSerializer<T>
- ObjectをJSON形式で保存する
- シリアライズするObjectの型を指定する必要がある

### GenericJackson2JsonRedisSerializer
- ObjectをJSON形式で保存する
- シリアライズするObjectの型を指定する必要がない
- 但しそのままではJSONからObjectにデシリアライズする時に型情報が無くて失敗してしまうので、`ObjectMapper#enableDefaultTyping()`を使って型情報を付ける設定にする必要がある
