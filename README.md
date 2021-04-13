此專案主要是用來放置 Spring boot 應用。

## spring-boot-employees-system

當中有三個分支
- logback-console
    - 實現 Log 以 Json 格式輸出，並透過 docker-compose 中 logging 將日誌傳輸到 fluent 並解析 Json
- logback-file
    - 實現將 Log 寫入定義的檔案中，藉由 fluent 套件 tail 進行讀取，此方式不需要 docker-compose 中 logging，但需要把應用程式的容器掛載出來 
- logback-fluent
    - 定義 fluent 的 host 和 port，當應用程式打包好後，可直接將日誌傳輸至 fluent 中，此方式不需要 docker-compose 中 logging

主要差異會在 pom.xml 的套件引入、logback-spring.xml 的設置和 application 屬性配置。