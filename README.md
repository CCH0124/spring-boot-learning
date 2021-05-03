此專案主要是用來放置 Spring boot 應用。

## spring-boot-employees-system

實現日誌整合 EFK。同時也整合 opentelemtry 將 MDC 整進 Log 中
- 實現 Log 以 Json 格式輸出
- 定義 fluent 的 host 和 port，當應用程式打包好後，可直接將日誌傳輸至 fluent 中，此方式不需要 docker-compose 中 logging

主要差異會在 pom.xml 的套件引入、logback-spring.xml 的設置和 application 屬性配置。同時有為 prod、dev 環境進行設置，prod 只會在出現 ERROR 以上等級才會將日誌顯示在控制台中，而 dev 則是 INFO等級以上。

## psql-redis-springboot

包含三個 docker-compose，logging 開頭用於 log 蒐集實驗用，tracing 則是分散式追蹤用，otel 同 tracing，但使用的組件不一樣，前者使用 jaeger 組件後者使用 opentelemtry 組件。

與 spring-boot-employees-system 的 logger 不同於，這邊不將 log 做任何處裡，而是透過 docker-compose 中 logging 的關鍵字，將應用程式 log 直接發送至 EFK 平台。log 分析這是由 EFK 組件負責。

## psql-spring-boot

以監控為實驗，同樣使用了 tracing 的功能，加上導入 actuator 結合 prometheus push gateway，將監控指標定期傳送至 prometheus。


## nginx
在此目錄下運行即可有反向代理，但需要注意 Port 映射

```bash
docker run -itd -p 80:80 -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf -v $(pwd)/security_header.conf:/etc/nginx/security_header.conf -v $(pwd)/sites-enabled/spring.conf:/etc/nginx/conf.d/default.conf nginx
```
