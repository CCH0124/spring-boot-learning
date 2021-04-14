此專案主要是用來放置 Spring boot 應用。

## spring-boot-employees-system

當中分支 logback 實現日誌整合 EFK。
- 實現 Log 以 Json 格式輸出
- 定義 fluent 的 host 和 port，當應用程式打包好後，可直接將日誌傳輸至 fluent 中，此方式不需要 docker-compose 中 logging

主要差異會在 pom.xml 的套件引入、logback-spring.xml 的設置和 application 屬性配置。
