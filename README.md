此專案主要是用來放置 Spring boot 應用。其中有些項目整合 Opentelemetry 的框架，如果針對 jaeger 進行配置則連線端點要指定端點

```yaml
OTEL_TRACES_EXPORTER: jaeger
OTEL_EXPORTER_JAEGER_ENDPOINT: http://192.168.101.129:9000 # must URI format
```
如果是 otel 協定則是以下設定，這邊 Opentelemetry 有提供 metric 規範因此可以導出至 prometheus。

```yml
OTEL_TRACES_EXPORTER: otlp 
OTEL_METRICS_EXPORTER: otlp # 如果有和 prometheus 結合
OTEL_EXPORTER_OTLP_ENDPOINT: http://192.168.101.129:4317
OTEL_EXPORTER_OTLP_TRACES_ENDPOINT: http://192.168.101.129:4317
OTEL_EXPORTER_OTLP_METRICS_ENDPOINT: http://192.168.101.129:4317 # 如果有和 prometheus 結合
```

## spring-boot-employees-system

實現日誌整合 EFK。同時也整合 OpenTelemetry 將 MDC 整進 Log 中，這樣 Log 訊息可以針對 Tracing 部分進行結合除錯
- 實現 Log 以 Json 格式輸出
- 定義 fluent 的 host 和 port，當應用程式打包好後，可直接將日誌傳輸至 fluent 中，此方式不需要在 docker-compose 中使用 logging 進行導出，其應用程式本身就會主動發 Log 至 fluent 中。

在 pom.xml 的套件引入有 logback-spring.xml 的設（針對 Log）置和 application 屬性配置。同時有為 prod、dev 環境進行設置，prod 只會在出現 ERROR 以上等級才會將日誌顯示在控制台中，而 dev 則是 INFO等級以上。

## psql-redis-springboot

包含三個 docker-compose，logging 開頭用於 log 蒐集實驗用，tracing 則是分散式追蹤用，otel 類似 tracing，但使用的組件不一樣，前者使用 jaeger 組件後者使用 OpenTelemetry 組件。

與 spring-boot-employees-system 的 logger 不同於，這邊不將 log 做任何處裡，而是透過 docker-compose 中 logging 的關鍵字，將應用程式 log 直接發送至 EFK 平台。log 分析這是由 EFK 組件負責。

## psql-spring-boot

主要以監控為實驗，同樣使用了 tracing 的功能這邊用的是 jaeger 方式，加上導入 actuator 結合 prometheus push gateway，將監控指標（JVM 等）定期傳送至 prometheus。


## nginx
在此目錄下運行即可有反向代理，但需要注意 Port 映射

```bash
docker run -itd -p 80:80 -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf -v $(pwd)/security_header.conf:/etc/nginx/security_header.conf -v $(pwd)/sites-enabled/spring.conf:/etc/nginx/conf.d/default.conf nginx
```
