============================================================
  Dubbo + Nacos Demo - 接口文档
============================================================

项目端口：
  dubbo-provider : 8081（业务接口 + 数据库操作）
  dubbo-consumer : 8082（Dubbo RPC 远程调用示例）

基础地址：
  Provider: http://localhost:8081
  Consumer: http://localhost:8082

============================================================
  一、Hello 服务（Consumer 8082，Dubbo RPC 调用）
============================================================

[1] 打招呼
  方法: GET
  地址: http://localhost:8082/hello?name=Tom
  说明: consumer 通过 Dubbo 远程调用 provider 的 HelloService
  示例: curl http://localhost:8082/hello?name=Tom
  返回: "Hello, Tom! (from Dubbo Provider)"


============================================================
  二、认证接口（Provider 8081，Shiro 登录/退出）
============================================================

[2] 登录
  方法: POST
  地址: http://localhost:8081/auth/login
  Body: {"username":"admin","password":"admin123"}
  说明: Shiro 认证，查询 user 表校验用户名密码
  示例:
    curl -X POST http://localhost:8081/auth/login ^
      -H "Content-Type: application/json" ^
      -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
  返回: {"success":true,"message":"登录成功","sessionId":"..."}

[3] 退出
  方法: POST
  地址: http://localhost:8081/auth/logout
  说明: 销毁当前 Shiro 会话
  示例: curl -X POST http://localhost:8081/auth/logout
  返回: {"success":true,"message":"已退出"}


============================================================
  三、用户管理（Provider 8081，user 表 CRUD）
============================================================

测试用户: username=admin, password=admin123

[4] 新增用户
  方法: POST
  地址: http://localhost:8081/user
  Body: {"username":"zhangsan","password":"123456","mobile":"13800001111","mail":"zs@test.com"}
  说明: 向 user 表插入记录，createtime 自动填充当前时间
  示例:
    curl -X POST http://localhost:8081/user ^
      -H "Content-Type: application/json" ^
      -d "{\"username\":\"zhangsan\",\"password\":\"123456\",\"mobile\":\"13800001111\",\"mail\":\"zs@test.com\"}"

[5] 删除用户
  方法: DELETE
  地址: http://localhost:8081/user/{userid}
  说明: 按 userid 删除 user 表记录
  示例: curl -X DELETE http://localhost:8081/user/2
  返回: "删除成功" 或 "记录不存在"

[6] 更新用户
  方法: PUT
  地址: http://localhost:8081/user/{userid}
  Body: {"username":"lisi","password":"654321","mobile":"13900002222","mail":"ls@test.com"}
  说明: 按 userid 更新 user 表记录
  示例:
    curl -X PUT http://localhost:8081/user/1 ^
      -H "Content-Type: application/json" ^
      -d "{\"username\":\"lisi\",\"password\":\"654321\",\"mobile\":\"13900002222\",\"mail\":\"ls@test.com\"}"
  返回: "更新成功" 或 "记录不存在"

[7] 查询单个用户
  方法: GET
  地址: http://localhost:8081/user/{userid}
  说明: 按 userid 查询 user 表
  示例: curl http://localhost:8081/user/1

[8] 查询全部用户
  方法: GET
  地址: http://localhost:8081/user
  说明: 查询 user 表全部记录
  示例: curl http://localhost:8081/user


============================================================
  四、人员管理（Provider 8081，person 表 CRUD）
============================================================

[9] 新增人员
  方法: POST
  地址: http://localhost:8081/person
  Body: {"name":"张三","age":25,"sex":"男","address":"北京市朝阳区","mobile":"13800003333","email":"zhangsan@test.com"}
  示例:
    curl -X POST http://localhost:8081/person ^
      -H "Content-Type: application/json" ^
      -d "{\"name\":\"张三\",\"age\":25,\"sex\":\"男\",\"address\":\"北京市朝阳区\",\"mobile\":\"13800003333\",\"email\":\"zhangsan@test.com\"}"

[10] 删除人员
  方法: DELETE
  地址: http://localhost:8081/person/{id}
  示例: curl -X DELETE http://localhost:8081/person/1

[11] 更新人员
  方法: PUT
  地址: http://localhost:8081/person/{id}
  Body: {"name":"李四","age":30,"sex":"女","address":"上海市浦东新区","mobile":"13900004444","email":"lisi@test.com"}
  示例:
    curl -X PUT http://localhost:8081/person/1 ^
      -H "Content-Type: application/json" ^
      -d "{\"name\":\"李四\",\"age\":30,\"sex\":\"女\",\"address\":\"上海市浦东新区\",\"mobile\":\"13900004444\",\"email\":\"lisi@test.com\"}"

[12] 查询单个人员
  方法: GET
  地址: http://localhost:8081/person/{id}
  示例: curl http://localhost:8081/person/1

[13] 查询全部人员
  方法: GET
  地址: http://localhost:8081/person
  示例: curl http://localhost:8081/person


============================================================
  五、数据表结构
============================================================

数据库: mydb (MySQL 8.x on 127.0.0.1:3306)
账号: root / root

[user 表]
  userid      BIGINT PK AUTO_INCREMENT  用户ID
  username    VARCHAR(50) NOT NULL       用户名
  password    VARCHAR(100) NOT NULL      密码
  createtime  DATETIME DEFAULT NOW      创建时间
  mobile      VARCHAR(20)               手机号
  mail        VARCHAR(100)              邮箱

[person 表]
  id       BIGINT PK AUTO_INCREMENT  主键
  name     VARCHAR(50) NOT NULL      姓名
  age      INT                       年龄
  sex      VARCHAR(10)               性别
  address  VARCHAR(200)              地址
  mobile   VARCHAR(20)               手机号
  email    VARCHAR(100)              邮箱


============================================================
  六、技术栈
============================================================

  Spring Boot 2.7.18
  Apache Dubbo 2.7.23
  Nacos 2.5.2（注册中心，127.0.0.1:8848）
  MyBatis 3.5.13 + Spring Boot Starter 2.3.1
  Apache Shiro 1.10.1（认证）
  MySQL 8.x + 本地 Maven 仓库缓存版驱动

============================================================