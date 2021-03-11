
建立資料庫和使用者
```shell
mysql> create user 'springcrud'@'%' identified by '123456';
Query OK, 0 rows affected (0.02 sec)

mysql> create database crudb;
Query OK, 1 row affected (0.00 sec)

mysql> grant all on crudb.* to 'springcrud'@'%';
Query OK, 0 rows affected (0.00 sec)
```

```shell
mysql> CREATE TABLE product ( id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45) NOT NULL, price FLOAT NOT NULL );
Query OK, 0 rows affected (0.04 sec)
mysql> show tables;
+-----------------+
| Tables_in_crudb |
+-----------------+
| product         |
+-----------------+
1 row in set (0.01 sec)
mysql> SHOW COLUMNS FROM product;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int         | NO   | PRI | NULL    | auto_increment |
| name  | varchar(45) | NO   |     | NULL    |                |
| price | float       | NO   |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+
3 rows in set (0.01 sec)
```

## CRUD
### Add
對映 controller 中 add 方法
```shell
$ curl -X POST -H "Content-Type: application/json" -d '{"name": "apple", "price": 189.8}' http://localhost:8080/products
```

```shell
mysql> use crudb;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> SELECT * FROM product;
+----+-------+-------+
| id | name  | price |
+----+-------+-------+
|  1 | apple | 189.8 |
+----+-------+-------+
1 row in set (0.00 sec)
```

對映 controller 中 list 方法
```shell
$ curl http://localhost:8080/products
[{"id":1,"name":"apple","price":189.8}]
```


```shell
$ curl -X POST -H "Content-Type: application/json" -d '{"name": "sony", "price": 120.86}' http://localhost:8080/products
$ curl -X POST -H "Content-Type: application/json" -d '{"name": "samsung", "price": 170.7}' http://localhost:8080/products
$ curl http://localhost:8080/products
[{"id":1,"name":"apple","price":189.8},{"id":2,"name":"sony","price":120.86},{"id":3,"name":"samsung","price":170.7}]
```

### Update

對映 controller 中 update 方法
```shell
$ curl -X PUT -H "Content-Type: application/json" -d '{"id": 1, "name": "iphone 12", "price": 999.7}' http://localhost:8080/products/1 -v
*   Trying 127.0.0.1...
* TCP_NODELAY set      
* Connected to localhost (127.0.0.1) port 8080 (#0)
> PUT /products/1 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 46
>
* upload completely sent off: 46 out of 46 bytes   
< HTTP/1.1 200
< Content-Length: 0
< Date: Sun, 06 Dec 2020 13:28:07 GMT        
<
* Connection #0 to host localhost left intact
```


```shell
$ curl http://localhost:8080/products
[{"id":1,"name":"iphone 12","price":999.7},{"id":2,"name":"sony","price":120.86},{"id":3,"name":"samsung","price":170.7}]
```

```shell
mysql> SELECT * FROM product;
+----+-----------+--------+
| id | name      | price  |
+----+-----------+--------+
|  1 | iphone 12 |  999.7 |
|  2 | sony      | 120.86 |
|  3 | samsung   |  170.7 |
+----+-----------+--------+
3 rows in set (0.01 sec)
```

當 id 不正確時回應 404

```shell
$ curl -X PUT -H "Content-Type: application/json" -d '{"id": 10, "name": "iphone 12", "price": 999.7}' http://localhost:8080/products/10 -v
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> PUT /products/10 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 47
>
* upload completely sent off: 47 out of 47 bytes
< HTTP/1.1 404
< Content-Length: 0
< Date: Sun, 06 Dec 2020 13:58:54 GMT
<
* Connection #0 to host localhost left intact
```

### Delete

對映 controller 中 deleteProductById 方法

```shell
$ curl -X DELETE http://localhost:8080/products/1
```

```mysql

mysql> SELECT * FROM product;
+----+---------+--------+
| id | name    | price  |
+----+---------+--------+
|  2 | sony    | 120.86 |
|  3 | samsung |  170.7 |
+----+---------+--------+
2 rows in set (0.00 sec)
```


```shell
$ curl http://localhost:8080/products/
[{"id":2,"name":"sony","price":120.86},{"id":3,"name":"samsung","price":170.7}]
```