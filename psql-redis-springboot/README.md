此專案包含兩個 docker-compose，logging 開頭用於 log 蒐集實驗用，tracing 則是分散式追蹤用。

```shell
postgres=# CREATE USER demo WITH PASSWORD '123456';
CREATE ROLE
postgres=# CREATE DATABASE demo WITH template=template0 owner=demo;
CREATE DATABASE
postgres=# \connect demo;
demo=# ALTER DEFAULT PRIVILEGES GRANT all ON SEQUENCES TO demo;
ALTER DEFAULT PRIVILEGES
```

查看建立的使用者
```shell
postgres=# \du
                                      List of roles
   Role name    |                         Attributes                         | Member of
----------------+------------------------------------------------------------+-----------
 demo           |                                                            | {}
 dev            |                                                            | {}
 expensetracker |                                                            | {}
 postgres       | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
```

##  POST /questions
```shell
$ curl --header "Content-Type: application/json" --request POST --data '{"title": "Hello JPA", "description":"I use psql with spring boot and JPA"}' http://localhost:8080/questions
{"createdAt":"2020-12-20T03:25:34.495+00:00","updatedAt":"2020-12-20T03:25:34.495+00:00","id":1000,"title":"Hello JPA","description":"I use psql with spring 
boot and JPA"}
```

## GET /questions/all

```shell
$ curl --request GET http://localhost:8080/questions/all | jq .
```

## POST /questions/{questionId}/answers
```shell
$ curl --header "Content-Type: application/json" --request POST --data '{"text": "So easy."}' http://localhost:8080/questions/1000/answers
{"createdAt":"2020-12-20T03:33:30.118+00:00","updatedAt":"2020-12-20T03:33:30.118+00:00","id":1000,"text":"So easy."}
```

## GET /questions/{questionId}/answers
```shell
$ curl --request GET 'http://localhost:8080/questions/1000/answers' | jq .
[
  {
    "createdAt": "2020-12-20T03:33:30.118+00:00",
    "updatedAt": "2020-12-20T03:33:30.118+00:00",
    "id": 1000,
    "text": "So easy."
  }
]
```

## GET /questions?title=&page=0&size=2

```shell

cch@LAPTOP-J7ES249S:/mnt/c/Users/ASUS/Desktop/Web/psql-redis-demo$ curl --request GET 'http://localhost:8080/questions?title=JAVA' | jq .
{
  "content": [
    {
      "createdAt": "2020-12-20T03:47:58.703+00:00",
      "updatedAt": "2020-12-20T04:34:08.296+00:00",
      "id": 1001,
      "title": "JAVA",
      "description": "psql, Spring boot, SPA"
    },
    {
      "createdAt": "2020-12-20T06:46:58.318+00:00",
      "updatedAt": "2020-12-20T06:46:58.318+00:00",
      "id": 1051,
      "title": "JAVA7",
      "description": "JAVA jdk7 with Spring boot"
    },
    {
      "createdAt": "2020-12-20T06:47:45.982+00:00",
      "updatedAt": "2020-12-20T06:47:45.982+00:00",
      "id": 1052,
      "title": "JAVA9",
      "description": "JAVA jdk9 with Spring boot"
    }
  ],
  "pageable": {
    "sort": {
      "unsorted": true,
      "sorted": false,
      "empty": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 3,
    "unpaged": false,
    "paged": true
  },
  "last": false,
  "totalPages": 2,
  "totalElements": 5,
  "size": 3,
  "number": 0,
  "first": true,
  "sort": {
    "unsorted": true,
    "sorted": false,
    "empty": true
  },
  "numberOfElements": 3,
  "empty": false
}
$ curl --request GET 'http://localhost:8080/questions?page=2&size=3' | jq .
{
  "content": [
    {
      "createdAt": "2020-12-20T06:49:29.854+00:00",
      "updatedAt": "2020-12-20T06:49:29.854+00:00",
      "id": 1056,
      "title": "PHP",
      "description": "PHP"
    },
    {
      "createdAt": "2020-12-20T06:49:53.057+00:00",
      "updatedAt": "2020-12-20T06:49:53.057+00:00",
      "id": 1057,
      "title": "javascript",
      "description": "javascript"
    },
    {
      "createdAt": "2020-12-20T06:50:23.471+00:00",
      "updatedAt": "2020-12-20T06:50:23.471+00:00",
      "id": 1058,
      "title": "node",
      "description": "node is javascript framework."
    }
  ],
  "pageable": {
    "sort": {
      "unsorted": true,
      "sorted": false,
      "empty": true
    },
    "offset": 6,
    "pageNumber": 2,
    "pageSize": 3,
    "unpaged": false,
    "paged": true
  },
  "last": true,
  "totalPages": 3,
  "totalElements": 9,
  "size": 3,
  "number": 2,
  "first": false,
  "sort": {
    "unsorted": true,
    "sorted": false,
    "empty": true
  },
  "numberOfElements": 3,
  "empty": false
}
```

## PSQL 
##### 查看表

```shell
demo=> \dt
         List of relations
 Schema |   Name    | Type  | Owner 
--------+-----------+-------+-------
 public | answers   | table | demo  
 public | questions | table | demo  
(2 rows)
```

##### 查看索引

```shell
demo=> \di
                  List of relations
 Schema |      Name      | Type  | Owner |   Table   
--------+----------------+-------+-------+-----------
 public | answers_pkey   | index | demo  | answers
 public | questions_pkey | index | demo  | questions
(2 rows)
```

##### 查看表結構 

```shell
demo=> \d
               List of relations
 Schema |       Name        |   Type   | Owner
--------+-------------------+----------+-------
 public | answer_sequence   | sequence | demo
 public | answers           | table    | demo
 public | question_sequence | sequence | demo
 public | questions         | table    | demo
(4 rows)
```

##### Answers 表插入的值

```shell
demo=> SELECT * FROM answers;
  id  |       created_at        |       updated_at        |     text      | question_id 
------+-------------------------+-------------------------+---------------+-------------
 1000 | 2020-12-20 11:33:30.118 | 2020-12-20 11:33:30.118 | So easy.      |        1000
 1001 | 2020-12-20 11:48:44.134 | 2020-12-20 11:48:44.134 | JAVA is good. |        1001
(2 rows)
```

##### Question 表插入的值
```shell
demo=> SELECT * FROM questions;
  id  |       created_at        |       updated_at        |             description             |   title   
------+-------------------------+-------------------------+-------------------------------------+-----------
 1000 | 2020-12-20 11:25:34.495 | 2020-12-20 11:25:34.495 | I use psql with spring boot and JPA | Hello JPA
 1001 | 2020-12-20 11:47:58.703 | 2020-12-20 11:47:58.703 | JAVA with Spring boot               | JAVA
(2 rows)
```

##### Join 關聯
```shell
demo=> SELECT q.title, q.description, a.text FROM questions as q JOIN answers as a ON q.id = a.id;
   title   |             description             |     text      
-----------+-------------------------------------+---------------
 Hello JPA | I use psql with spring boot and JPA | So easy.
 JAVA      | JAVA with Spring boot               | JAVA is good.
(2 rows)
```


## Redis

以下是查詢過的 URL，並且緩存至 redis。

查看所有 Key
```shell
127.0.0.1:6379> keys *
1) "answers::1051"
2) "answers::1101"
3) "answers::1000"
4) "answers::1053"
```

顯示 Key 值
```shell
127.0.0.1:6379> GET answers::1051
"\xac\xed\x00\x05sr\x00\x13java.util.ArrayListx\x81\xd2\x1d\x99\xc7a\x9d\x03\x00\x01I\x00\x04sizexp\x00\x00\x00\x01w\x04\x00\x00\x00\x01sr\x00*com.example.cch.psqlredisdemo.model.Answer\x04\x0e\x01\x8a\xd6y\x02\xb8\x02\x00\x03L\x00\x02idt\x00\x10Ljava/lang/Long;L\x00\bquestiont\x00.Lcom/example/cch/psqlredisdemo/model/Question;L\x00\x04textt\x00\x12Ljava/lang/String;xr\x00.com.example.cch.psqlredisdemo.model.AuditModel\xe11\x01N\xd5B9\xac\x02\x00\x02L\x00\tcreatedAtt\x00\x10Ljava/util/Date;L\x00\tupdatedAtq\x00~\x00\axpsr\x00\x12java.sql.Timestamp&\x18\xd5\xc8\x01S\xbfe\x02\x00\x01I\x00\x05nanosxr\x00\x0ejava.util.Datehj\x81\x01KYt\x19\x03\x00\x00xpw\b\x00\x00\x01v~\xebVhx7\xf8\x00\xc0sq\x00~\x00\tw\b\x00\x00\x01v~\xebVhx7\xf8\x00\xc0sr\x00\x0ejava.lang.Long;\x8b\xe4\x90\xcc\x8f#\xdf\x02\x00\x01J\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x00\x00\x00\x00\x04\x1bsr\x004org.hibernate.proxy.pojo.bytebuddy.SerializableProxy\xc8\xe2\xf1\xff\xb6\x19\xaa.\x02\x00\bL\x00\x0fcomponentIdTypet\x00\"Lorg/hibernate/type/CompositeType;L\x00\x1bidentifierGetterMethodClasst\x00\x11Ljava/lang/Class;L\x00\x1aidentifierGetterMethodNameq\x00~\x00\x05L\x00\x1bidentifierSetterMethodClassq\x00~\x00\x12L\x00\x1aidentifierSetterMethodNameq\x00~\x00\x05[\x00\x1cidentifierSetterMethodParamst\x00\x12[Ljava/lang/Class;[\x00\ninterfacesq\x00~\x00\x13L\x00\x0fpersistentClassq\x00~\x00\x12xr\x00-org.hibernate.proxy.AbstractSerializableProxy\x92j-ZE\x0b\x04a\x02\x00\x05Z\x00\x1ballowLoadOutsideTransactionL\x00\nentityNameq\x00~\x00\x05L\x00\x02idt\x00\x16Ljava/io/Serializable;L\x00\breadOnlyt\x00\x13Ljava/lang/Boolean;L\x00\x12sessionFactoryUuidq\x00~\x00\x05xp\x00t\x00,com.example.cch.psqlredisdemo.model.Questionsq\x00~\x00\r\x00\x00\x00\x00\x00\x00\x04\x1bsr\x00\x11java.lang.Boolean\xcd r\x80\xd5\x9c\xfa\xee\x02\x00\x01Z\x00\x05valuexp\x00ppvr\x00,com.example.cch.psqlredisdemo.model.QuestionZ\xfd\x1be\x81\x89\xaa\xf9\x02\x00\x03L\x00\x0bdescriptionq\x00~\x00\x05L\x00\x02idq\x00~\x00\x03L\x00\x05titleq\x00~\x00\x05xq\x00~\x00\x06t\x00\x05getIdq\x00~\x00\x1dt\x00\x05setIdur\x00\x12[Ljava.lang.Class;\xab\x16\xd7\xae\xcb\xcdZ\x99\x02\x00\x00xp\x00\x00\x00\x01vq\x00~\x00\ruq\x00~\x00 \x00\x00\x00\x01vr\x00\"org.hibernate.proxy.HibernateProxyQ~6&\x97\x1b\xbe\"\x02\x00\x00xpq\x00~\x00\x1dt\x00\bis JAVA7x"
```

查看類型

```shell
127.0.0.1:6379> TYPE answers::1051
string
```
## 筆記
- consumes 限制 Content-Type 為 JSON
- produces 限定返回為 JSON 對應 ACCEPT


## 參考資源
- [baeldung jpa-entity-lifecycle-events](https://www.baeldung.com/jpa-entity-lifecycle-events)
- [baeldung database-auditing-jpa](https://www.baeldung.com/database-auditing-jpa)
- [effective-restful-search-api-in-spring](https://blog.tratif.com/2017/11/23/effective-restful-search-api-in-spring/)
- [hibernate-jpa-entity-listeners](https://matthung0807.blogspot.com/2018/07/hibernate-jpa-entity-listeners.html)
