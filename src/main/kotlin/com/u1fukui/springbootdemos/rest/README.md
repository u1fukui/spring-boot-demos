## Jackson
### Kotlin で利用する
- Kotlin から利用する場合、API Response を data class にうまくマッピング出来なくてエラーになるので、`com.fasterxml.jackson.module:jackson-module-kotlin` を使う
  - 使い方は https://github.com/FasterXML/jackson-module-kotlin に書いてある

### SnakeCase  
- `RestTemplate` はデフォルトだと、JSON のプロパティ名を CamelCase として解析しようとするので、SnakeCase などの場合は `ObjectMapper#setPropertyNamingStrategy(PropertyNamingStrategy)` で設定が必要

### JSON 中に、クラスに定義されていないプロパティがある場合
- デフォルトだとエラーになるので、`ObjectMapper#configure(DeserializationFeature, boolean)` で設定が必要