# Copilot Java Demo 2

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.11-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8.6-orange.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0.28-blue.svg)](https://www.mysql.com/)
[![Flyway](https://img.shields.io/badge/Flyway-6.5.7-red.svg)](https://flywaydb.org/)

一个完整的Spring Boot用户管理系统示例项目，包含用户和部门管理功能，**完美解决中文字符集显示问题**。

## 🚀 项目特性

### 核心功能
- **用户管理**: 完整的CRUD操作，包含用户注册、更新、删除和查询
- **部门管理**: 部门信息的增删改查功能
- **数据库迁移**: 使用Flyway进行版本化数据库管理
- **API文档**: 集成Swagger/Knife4j提供在线API文档

### 🔥 中文支持优化
- **中文乱码完美解决方案**: 彻底解决Windows PowerShell中文输出乱码问题
- **UTF-8启动脚本**: 提供`.ps1`和`.bat`启动脚本自动配置编码
- **跨平台兼容**: 统一的文件编码标准，支持Windows/Linux/macOS
- **生产环境配置**: 针对生产部署的专用配置文件

## 🛠️ 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.2.11.RELEASE | 核心框架 |
| Maven | 3.8.6+ | 构建工具 |
| MyBatis Plus | 3.2.0 | ORM框架 |
| MySQL | 8.0.28+ | 数据库 |
| Flyway | 6.5.7 | 数据库迁移 |
| Druid | 1.2.8 | 连接池 |
| Swagger | 2.9.2 | API文档 |
| Knife4j | 2.0.4 | API文档UI |
| Hutool | 5.7.19 | 工具库 |

## 📦 快速开始

### 1. 环境要求
```bash
Java 8+
Maven 3.6+
MySQL 8.0+
```

### 2. 克隆项目
```bash
git clone https://github.com/你的用户名/copilot_java_demo2.git
cd copilot_java_demo2
```

### 3. 数据库配置
修改 `src/main/resources/application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/copilot_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 运行项目

#### 🎯 方式1: 使用启动脚本（推荐，解决中文乱码）
```powershell
# PowerShell脚本
.\start-utf8.ps1

# 或批处理脚本
.\start-utf8.bat
```

#### 方式2: Maven命令
```bash
# 设置UTF-8编码并启动
chcp 65001
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dfile.encoding=UTF-8"
```

#### 方式3: JAR包运行
```bash
# 先打包
mvn clean package -DskipTests

# 设置编码并运行
chcp 65001
java -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -jar target\copilot-java-demo2-1.0.0-SNAPSHOT.jar
```

### 5. 访问应用
- **应用首页**: http://localhost:8888
- **API文档**: http://localhost:8888/doc.html
- **Swagger UI**: http://localhost:8888/swagger-ui.html

## 🔧 解决中文乱码

### 问题描述
在Windows PowerShell中运行Java应用时，中文输出显示为乱码。

### 🎯 解决方案

#### 一键解决（推荐）
使用项目提供的启动脚本：
```powershell
.\start-utf8.ps1  # PowerShell
.\start-utf8.bat  # 命令提示符
```

#### 手动解决
```powershell
# 1. 设置控制台编码为UTF-8
chcp 65001

# 2. 使用UTF-8参数启动Java应用
java -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -jar target\copilot-java-demo2-1.0.0-SNAPSHOT.jar
```

#### 完整启动参数
```bash
java -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -jar target\copilot-java-demo2-1.0.0-SNAPSHOT.jar
```

## 📁 项目结构

```
copilot_java_demo2/
├── src/
│   ├── main/
│   │   ├── java/org/suntek/
│   │   │   ├── controller/     # 控制器层
│   │   │   ├── service/        # 服务层
│   │   │   ├── model/          # 数据模型
│   │   │   └── config/         # 配置类
│   │   └── resources/
│   │       ├── db/migration/   # Flyway数据库迁移脚本
│   │       ├── mapper/         # MyBatis映射文件
│   │       ├── application.yml # 主配置文件
│   │       └── application-prod.yml # 生产环境配置
│   └── test/                   # 单元测试
├── start-utf8.ps1             # PowerShell启动脚本
├── start-utf8.bat             # 批处理启动脚本
├── MAVEN_COMMANDS.md          # Maven命令手册
├── .gitattributes             # Git文件属性配置
├── .editorconfig              # 编辑器配置
└── pom.xml                    # Maven配置文件
```

## 🚀 部署指南

### 开发环境
```bash
# 启动开发环境
mvn spring-boot:run
```

### 生产环境
```bash
# 1. 打包应用
mvn clean package -Pprod

# 2. 运行应用
java -jar target/copilot-java-demo2-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
```

## 📖 API文档

项目集成了Swagger和Knife4j，提供完整的API文档：

- **在线文档**: http://localhost:8888/doc.html
- **接口测试**: 支持在线测试所有API接口
- **参数说明**: 详细的请求/响应参数说明

### 主要接口

#### 用户管理
- `GET /api/users` - 获取用户列表
- `POST /api/users` - 创建用户
- `PUT /api/users/{id}` - 更新用户
- `DELETE /api/users/{id}` - 删除用户

#### 部门管理
- `GET /api/departments` - 获取部门列表
- `POST /api/departments` - 创建部门
- `PUT /api/departments/{id}` - 更新部门
- `DELETE /api/departments/{id}` - 删除部门

## 🧪 测试

### 运行单元测试
```bash
mvn test
```

### 运行指定测试
```bash
# 运行用户服务测试
mvn test -Dtest=UserServiceImplTest

# 运行部门服务测试
mvn test -Dtest=DepartmentServiceImplTest
```

## 🔍 常见问题

### Q: 中文输出乱码怎么办？
A: 使用项目提供的 `start-utf8.ps1` 启动脚本，或手动设置 `chcp 65001` 后运行。

### Q: 数据库连接失败？
A: 检查 `application.yml` 中的数据库连接配置，确保MySQL服务已启动。

### Q: Flyway迁移失败？
A: 检查数据库权限，或使用 `mvn flyway:repair` 修复迁移历史。

### Q: 端口冲突？
A: 修改 `application.yml` 中的 `server.port` 配置。

## 编程理念

本项目采用GitHub Copilot Vibe Coding风格开发，体现了AI辅助编程的最佳实践。在开发过程中，我们充分利用AI的智能提示和代码生成能力，结合人工的架构设计和业务逻辑思考，实现高效、优雅的代码编写体验。

项目代码注重以下特点：
- **语义化编程**：函数和变量命名清晰表达业务意图，便于AI理解和辅助
- **结构化设计**：遵循标准的分层架构，让AI能够准确识别代码上下文
- **注释驱动**：通过恰当的注释引导AI生成符合预期的代码实现
- **迭代优化**：在AI建议的基础上，结合业务需求进行持续改进

## 📝 更新日志

### v1.0.0 (2025-08-08)
- ✨ 初始版本发布
- 🔥 完美解决中文乱码问题
- 📚 完善的Maven命令文档
- 🚀 生产环境部署配置
- 🧪 完整的单元测试覆盖

## 👨‍💻 作者

**陈奇毅** - *Initial work* - [GitHub](https://github.com/你的用户名)

## 🙏 致谢

- Spring Boot团队提供的优秀框架
- MyBatis Plus简化的ORM操作
- Flyway提供的数据库迁移方案
- 所有为开源社区做出贡献的开发者们

---

⭐ 如果这个项目对你有帮助，请给个Star支持一下！
