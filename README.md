此專案主要是用來放置 Spring boot 應用。

## spring-boot-employees-system

當中分支 logback 實現日誌整合 EFK。同時也整合 opentelemtry 將 MDC 整進 Log 中
- 實現 Log 以 Json 格式輸出
- 定義 fluent 的 host 和 port，當應用程式打包好後，可直接將日誌傳輸至 fluent 中，此方式不需要 docker-compose 中 logging

主要差異會在 pom.xml 的套件引入、logback-spring.xml 的設置和 application 屬性配置。
主要差異會在 pom.xml 的套件引入、logback-spring.xml 的設置和 application 屬性配置。同時有為 prod、dev 環境進行設置，prod 只會在出現 ERROR 以上等級才會將日誌顯示在控制台中，而 dev 則是 INFO等級以上。

## nginx
在此目錄下運行即可有反向代理，但需要注意 Port 映射

```bash
docker run -itd -p 80:80 -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf -v $(pwd)/security_header.conf:/etc/nginx/security_header.conf -v $(pwd)/sites-enabled/spring.conf:/etc/nginx/conf.d/default.conf nginx
```
