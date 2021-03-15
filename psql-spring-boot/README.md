##### Ubuntu

```shell
$ docker volume create --name postgresql-data
$ docker run -it --rm -v postgresql-data:/var/lib/postgresql/data -e POSTGRES_PASSWORD=123456 postgres
$ sudo docker run -it --rm -v postgresql-data:/var/lib/postgresql/data bash chown -R 1000:1000 /var/lib/postgresql/data
$ sudo docker run -it -d -p 5432:5432 --user 1000:1000 -v postgresql-data:/var/lib/postgresql/data postgres
```

##### windows WSL

[安裝](https://www.postgresql.org/download/linux/ubuntu/)客戶端連線

```shell
$ psql -U postgres -h 192.168.134.146 -p 5432
```

建立資料庫

```shell
postgres=# CREATE DATABASE employees;
CREATE DATABASE
postgres=# CREATE USER dev WITH ENCRYPTED PASSWORD '123456';
CREATE ROLE
postgres=# GRANT ALL PRIVILEGES ON DATABASE employees TO dev;
GRANT
```

```shell
$ psql -U dev -h 192.168.134.146 -p 5432 employees
Password for user dev: 
psql (13.1 (Ubuntu 13.1-1.pgdg18.04+1))
Type "help" for help.

employees=> 
```


## CRUD Test
### POST
Error
```shell
$ curl --header "Content-Type: application/json" \
> --request POST \
> --data '{"firstName":"Chen", "lastName":"Kevin", "email": "123@gmail.com"}' \
> http://127.0.0.1:8080
{"timestamp":"2020-12-15T06:09:58.323+00:00","status":404,"error":"Not Found","message":"","path":"/"}
```

```shell
$ curl --header "Content-Type: application/json" \
> --request POST \
> --data '{"firstName":"Chen", "lastName":"Kevin", "email": "123@gmail.com"}' \
> http://127.0.0.1:8080/api/v1/employees
{"id":1,"firstName":"Chen","lastName":"Kevin","email":"123@gmail.com"}
```

```shell
$ curl --header "Content-Type: application/json" --request POST -v --data '{"firstName":"Chen", "lastName":"Naruto", "email": "naruto@gmail.com"}' http://127.0.0.1:8080/api/v1/employees
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 8080 (#0)
> POST /api/v1/employees HTTP/1.1
> Host: 127.0.0.1:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 70
>
* upload completely sent off: 70 out of 70 bytes
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Tue, 15 Dec 2020 06:11:57 GMT
<
* Connection #0 to host 127.0.0.1 left intact
```

```shell
$ curl --header "Content-Type: application/json" --request POST --data '{"firstName":"Itachi", "lastName":"madara", "email": "madara@gmail.com"}' http://127.0.0.1:8080/api/v1/employees
{"id":3,"firstName":"Itachi","lastName":"madara","email":"madara@gmail.com"}
```

```shell
employees=> \d
              List of relations
 Schema |       Name       |   Type   | Owner 
--------+------------------+----------+-------
 public | employees        | table    | dev   
 public | employees_id_seq | sequence | dev
(2 rows)
```

```shell
employees=> SELECT * FROM employees;
 id |      email       | first_name | last_name 
----+------------------+------------+-----------
  1 | 123@gmail.com    | Chen       | Kevin     
  2 | naruto@gmail.com | Chen       | Naruto    
  3 | madara@gmail.com | Itachi     | madara    
(3 rows)
```

獲取所有員工
```shell
$ curl http://127.0.0.1:8080/api/v1/employees
[{"id":1,"firstName":"Chen","lastName":"Kevin","email":"123@gmail.com"},{"id":2,"firstName":"Chen","lastName":"Naruto","email":"naruto@gmail.com"},{"id":3,"firstName":"Itachi","lastName":"madara","email":"madara@gmail.com"}]
```
### Get
使用 Id 取得員工

```shell
$ curl --request GET http://127.0.0.1:8080/api/v1/employees/1
{"id":1,"firstName":"Chen","lastName":"Kevin","email":"123@gmail.com"}
```

### Update

```shell
$ curl --header "Content-Type: application/json" --request PUT --data '{"firstName":"Itachi", "lastName":"madara", "email": "madara234@gmail.com"}' http://127.0.0.1:8080/api/v1/employees/3
{"id":3,"firstName":"Itachi","lastName":"madara","email":"madara234@gmail.com"}
```

```shell
employees=> SELECT * FROM employees;
 id |        email        | first_name | last_name 
----+---------------------+------------+-----------
  1 | 123@gmail.com       | Chen       | Kevin     
  2 | naruto@gmail.com    | Chen       | Naruto    
  3 | madara234@gmail.com | Itachi     | madara     # Mail 已經更換
(3 rows)
```
### Delete

```shell
$ curl --request DELETE  http://127.0.0.1:8080/api/v1/employees/2
{"delete":true}
```

```shell
employees=> SELECT * FROM employees;
 id |        email        | first_name | last_name 
----+---------------------+------------+-----------
  1 | 123@gmail.com       | Chen       | Kevin
  3 | madara234@gmail.com | Itachi     | madara
(2 rows)
```

```shell
$ curl --request GET  http://127.0.0.1:8080/api/v1/employees/
[{"id":1,"firstName":"Chen","lastName":"Kevin","email":"123@gmail.com"},{"id":3,"firstName":"Itachi","lastName":"madara","email":"madara234@gmail.com"}]
```
## Docker

```shell
docker build -t web .
```
run image

```shell
docker run -itd -e SPRING_DATASOURCE_URL=jdbc:postgresql://192.168.101.129:5432/employees -e SPRING_DATASOURCE_USERNAME=dev -e SPRING_DATASOURCE_PASSWORD=123456 -p 8080:8080 web:latest
```
## Jaeger 配置
引入 `opentelemetry-javaagent-all.jar` 透過 `-javaagent:opentelemetry-javaagent-all.jar` 運行。起 docker-compose 在配置 `OTEL_TRACES_EXPORTER: jaeger` 說明使用哪一種工具`OTEL_EXPORTER_JAEGER_ENDPOINT: http://192.168.101.129:9000` 連線到 Jaeger 的搜集器，最後透過 jaeger 儲存到 Elasticsearch。

Jaeger exporter 目前只支援 gRPC 連線

[opentelemetry-java](https://github.com/open-telemetry/opentelemetry-java/blob/main/sdk-extensions/autoconfigure/README.md)

## Metric

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
		</dependency>
		<dependency>
			<groupId>io.prometheus</groupId>
			<artifactId>simpleclient_pushgateway</artifactId>
		</dependency>
```

屬性配置

```yaml
management.endpoints.web.exposure.include=prometheus
management.metrics.export.prometheus=true
management.metrics.export.prometheus.pushgateway.base-url=localhost:9091 # metric 傳給 pushgateway，由 Prometheus 抓取值
management.metrics.export.prometheus.pushgateway.enabled=true
management.metrics.export.prometheus.pushgateway.push-rate=1m
management.metrics.export.prometheus.pushgateway.shutdown-operation=push
```

## 問題解決

```
spring.datasource.password # Login password of the database.
spring.datasource.data-password # execute DML scripts
```
