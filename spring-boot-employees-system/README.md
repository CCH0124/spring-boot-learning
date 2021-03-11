簡單的 Spring boot 實現 CRUD。使用 JPA 以及 thymeleaf 等套件。 

## MySQL
MySQL 使用 Docker 架設

安裝至虛擬機

```shell
sudo docker run --name mysql -p 3306:3306 -v /mnt/mysql-data:/var/lib/mysql -v /mnt/mysql-config:/etc/mysql/conf.d  -e MYSQL_ROOT_PASSWORD=123456 --user 1000:1000 -d mysql:8.0
```
Windows 的 WSL 安裝此套件，以進行遠端

```shell
 sudo apt-get install mysql-client
```
遠端登入 Mysql

```shell
mysql -h localhost -P 3306 --protocol=tcp -u spring -p
```

以下是 MySQL 遠端操作結果

```shell
$ mysql -h 192.168.134.146 -P 3306 --protocol=tcp -u spring -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 93
Server version: 8.0.22 MySQL Community Server - GPL

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.  

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show tables;
ERROR 1046 (3D000): No database selected
mysql> use demo
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> use demo;
Database changed
mysql> show tables;
+----------------+
| Tables_in_demo |
+----------------+
| employees      |
+----------------+
1 row in set (0.01 sec)

mysql> show tables
    -> ;
+----------------+
| Tables_in_demo |
+----------------+
| employees      |
+----------------+
1 row in set (0.01 sec)

mysql> SELECT * FROM employees
    -> ;
+----+-------+----------------------------+-----------+
| id | email | first_name                 | last_name |
+----+-------+----------------------------+-----------+
|  1 | NULL  | Itachi,kevin,123@gmail.com | NULL      |
+----+-------+----------------------------+-----------+
1 row in set (0.01 sec)

mysql> SELECT * FROM employees;
+----+-----------------+------------+-----------+
| id | email           | first_name | last_name |
+----+-----------------+------------+-----------+
|  1 | 22123@gmail.com | Itachi     | kevinCC   |
|  2 | aaa@gmail.com   | Hello      | ASA       |
|  3 | 123@gmail.com   | Itachi     | Kevin     |
|  4 | 123@gmail.com   | Itachi     | Kevinss   |
+----+-----------------+------------+-----------+
4 rows in set (0.00 sec)

mysql> SELECT * FROM employees;
+----+-----------------+------------+-----------+
| id | email           | first_name | last_name |
+----+-----------------+------------+-----------+
|  1 | 22123@gmail.com | Itachi     | kevinCC   |
|  2 | aaa@gmail.com   | Hello      | ASA       |
|  4 | 123@gmail.com   | Itachi     | Kevinss   |
+----+-----------------+------------+-----------+
3 rows in set (0.00 sec)

mysql>
```

這邊雖然有 root，但還是建立一個使用者 spring，創建如下

```shell
create user 'spring'@'%' identified by '123456';
grant all on demo.* to 'spring'@'%'; 
```


## Home Page
![](/image/HomePage.png)

## Add Employee Page
![](/image/addPage.png)

## Update Page
![](/image/updatePage.png)
