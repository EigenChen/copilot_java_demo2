# 用户管理模块示例项目

## 项目简介

这是一个基于Spring Boot 2.2.11 + MyBatis Plus的用户管理模块示例项目，实现了用户的增删改查功能。

## 编程理念

本项目采用GitHub Copilot Vibe Coding风格开发，体现了AI辅助编程的最佳实践。在开发过程中，我们充分利用AI的智能提示和代码生成能力，结合人工的架构设计和业务逻辑思考，实现高效、优雅的代码编写体验。

项目代码注重以下特点：
- **语义化编程**：函数和变量命名清晰表达业务意图，便于AI理解和辅助
- **结构化设计**：遵循标准的分层架构，让AI能够准确识别代码上下文
- **注释驱动**：通过恰当的注释引导AI生成符合预期的代码实现
- **迭代优化**：在AI建议的基础上，结合业务需求进行持续改进

这种人机协作的编程模式不仅提升了开发效率，更重要的是保证了代码质量和可维护性，为现代软件开发提供了新的思路和方法。

## 技术栈

- JDK 8
- Spring Boot 2.2.11
- MyBatis Plus 3.2.0
- MySQL 8.0
- Druid连接池
- Swagger2 + Knife4j
- Flyway数据库版本管理
- Hutool工具库
- FastJson
- Lombok

## 项目结构

```
src/main/java/org/suntek/
├── common/           # 公共组件
├── config/           # 配置类
├── controller/       # 控制器
├── service/          # 服务接口层
│   └── impl/         # 服务实现层
├── mapper/           # 数据访问层
├── model/            # 数据模型
│   ├── entity/       # 实体类
│   ├── dto/          # 数据传输对象
│   └── vo/           # 视图对象
└── util/             # 工具类
```

## 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE copilot_demo CHARACTER SET utf8 COLLATE utf8_general_ci;
```

2. 修改 `application.yml` 中的数据库连接配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/copilot_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: 123456
```

## 启动应用

1. 确保MySQL服务已启动
2. 运行以下命令启动应用：
```bash
mvn spring-boot:run
```

应用将在端口8888上启动。

## API文档

启动应用后，访问以下地址查看API文档：
- Knife4j UI: http://localhost:8888/doc.html

## 用户表结构

用户表 `T_USER` 包含以下字段：

- `TID`: 主键ID（自增）
- `USER_CODE`: 用户账号（唯一）
- `USER_NAME`: 用户姓名
- `BIRTH_DATE`: 出生日期
- `GENDER`: 性别（1-男，2-女）
- `NATION`: 民族
- `MOBILE`: 手机号
- `EMAIL`: 邮箱
- `CREATE_TIME`: 创建时间
- `UPDATE_TIME`: 更新时间

## API接口说明

### 1. 查询用户信息
- **接口**: `GET /api/user/getUserInfo`
- **参数**: `userCode` - 用户账号
- **说明**: 根据用户账号查询用户详细信息

### 2. 分页查询用户列表
- **接口**: `GET /api/user/getUserList`
- **参数**: 
  - `keyword` - 关键字检索（可选）
  - `pageNum` - 页码（默认1）
  - `pageSize` - 每页条数（默认10）
- **说明**: 支持按关键字检索用户账号、姓名、手机号

### 3. 复杂条件分页查询
- **接口**: `POST /api/user/getUserListByCondition`
- **参数**: UserListQo对象
- **说明**: 支持多条件组合查询

### 4. 添加用户
- **接口**: `POST /api/user/addUser`
- **参数**: UserDto对象
- **说明**: 新增用户，用户账号不能重复

### 5. 更新用户信息
- **接口**: `POST /api/user/updateUser`
- **参数**: UserDto对象
- **说明**: 更新用户信息，需要提供用户ID

### 6. 删除用户
- **接口**: `POST /api/user/deleteUser`
- **参数**: `tid` - 用户ID
- **说明**: 根据用户ID删除用户

## 测试数据

项目启动时会自动创建测试数据：

1. 管理员账号：`admin`，密码：无，姓名：管理员
2. 普通用户：`user001`，姓名：张三
3. 普通用户：`user002`，姓名：李四

## 开发规范

项目严格遵循 `DEVELOPMENT_RULES.md` 中定义的开发规范，包括：

- SOLID、DRY、KISS、YAGNI原则
- 代码分层架构规范
- 命名规范
- 注释规范
- 异常处理规范
- 数据库设计规范

## 日志配置

项目使用Spring Boot默认的Logback日志框架，日志级别配置在 `application.yml` 中。

## 构建和部署

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包项目
mvn clean package

# 运行JAR包
java -jar target/copilot-java-demo2-1.0.0-SNAPSHOT.jar
```
