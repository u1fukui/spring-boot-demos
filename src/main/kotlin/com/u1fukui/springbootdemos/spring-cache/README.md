# Spring Cache

## 概要
- https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache
- https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html


## 実際に行った手順
### 1. spring-boot-starter-cache を使う。
`implementation 'org.springframework.boot:spring-boot-starter-cache'`

### 2. CacheManager を設定する
- キャッシュの設定は、`CacheManager`のBeanを作る時に設定する
  - `SpringCacheConfig` 参照
- 今回はRedisを使ってキャッシュを実現している　 
  - Redisの設定は、[別の箇所](../redis)で設定済み

### 3. @Cacheable をキャッシュ対象メソッドに付ける
- 今回はGitHub APIを実行して、そのレスポンス(JSON)をキャッシュするようにしている
- なので、APIを実行してそのレスポンスを返すメソッドに対して`@Cacheable`を付けている
- そうすると、キャッシュが存在する時はキャッシュを返し、存在しない時はそのメソッドを実行するようになる
- `@Cacheable`の詳細な仕様は[こちら](https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-annotations-cacheable)

### 4. キャッシュするオブジェクトクラスを Serializable にする
- `Serializable` にしないと、キャッシュ時に以下のような例外が発生する
```
Cannot serialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to serialize object using DefaultSerializer; nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.u1fukui.springbootdemos.dto.RepositorySearchResult]
```

## 気をつけること

### 同一オブジェクト内のメソッド呼び出しは Spring AOP が効かない
- `@Cacheable`は、Spring AOPによって実現されているため、同一オブジェクト内からそのメソッドを呼び出した場合はキャッシュが効かない
  - 詳細: https://engineering.linecorp.com/ja/blog/spring-boot-job-report/
  - Spring AOP では内部で CGlib と呼ばれるコード生成ライブラリを用いて proxy と呼ばれるクラスでラップされます。 アスペクトによる処理はこのラッパー部分に編み込まれます。 そして DI からはそのラッパーオブジェクトが inject されます。
  - そのため、DIが注入した参照を通してメソッドを呼び出した場合は proxy を介すため AOPによる処理が働きますが、 thisによる参照を通してメソッドの呼び出しを行う場合には proxy が経由されずAOP処理が効きません。
