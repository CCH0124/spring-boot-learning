## Connect Config

[程式碼鏈結](/src/main/java/com/example/cch/mongodemo/config/MongoConfig.java)，當中 `mongoClient` 是一個連線配置設定檔。 `getMappingBasePackages` 是用來設定其相關實體類的程式碼位置。

## Query MongoDB

[與 MongoDB 交互查詢資料的範例](/src/main/java/com/example/cch/mongodemo/mongo/service/impl/GnssServiceImpl.java)，這邊使用了 `Query`、`Bson` 和 `lookup` (join) 方式。


## Problem
- Date range use Query method is fail, then use Bson method is success.

待釐清問題，目前認為是型態問題導致。
