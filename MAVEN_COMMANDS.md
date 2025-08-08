# Maven 常用命令手册

## 📋 项目基础操作

### 清理与编译
```bash
# 清理编译产物
mvn clean

# 编译源代码
mvn compile

# 编译测试代码
mvn test-compile

# 清理并重新编译
mvn clean compile
```

### 测试相关
```bash
# 运行所有单元测试
mvn test

# 运行指定测试类
mvn test -Dtest=UserServiceTest

# 运行指定测试方法
mvn test -Dtest=UserServiceTest#testCreateUser

# 跳过测试编译和执行
mvn compile -Dmaven.test.skip=true

# 编译但跳过测试执行
mvn compile -DskipTests=true
```

### 打包与安装
```bash
# 打包项目（生成jar/war）
mvn package

# 跳过测试打包
mvn package -Dmaven.test.skip=true

# 安装到本地仓库
mvn install

# 清理、编译、测试、打包、安装（完整流程）
mvn clean install
```

## 🚀 Spring Boot 专用命令

### 应用运行
```bash
# 启动Spring Boot应用
mvn spring-boot:run

# 指定profile启动
mvn spring-boot:run -Dspring.profiles.active=dev

# 指定端口启动
mvn spring-boot:run -Dserver.port=9999

# 调试模式启动
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### 应用打包
```bash
# 重新打包为可执行jar
mvn spring-boot:repackage

# 构建Docker镜像（需要配置）
mvn spring-boot:build-image
```

## 🗄️ 数据库迁移（Flyway）

### Flyway操作
```bash
# 查看迁移状态
mvn flyway:info

# 执行数据库迁移
mvn flyway:migrate

# 验证迁移
mvn flyway:validate

# 清空数据库（危险操作！）
mvn flyway:clean

# 修复迁移历史
mvn flyway:repair

# 创建基线版本
mvn flyway:baseline
```

### Flyway配置示例
```bash
# 指定数据库URL执行迁移
mvn flyway:migrate -Dflyway.url=jdbc:mysql://localhost:3306/testdb

# 指定用户名密码
mvn flyway:migrate -Dflyway.user=root -Dflyway.password=123456
```

## 🔍 项目分析与依赖管理

### 依赖分析
```bash
# 查看依赖树
mvn dependency:tree

# 分析依赖关系
mvn dependency:analyze

# 解析依赖
mvn dependency:resolve

# 下载源码
mvn dependency:sources

# 下载javadoc
mvn dependency:resolve -Dclassifier=javadoc

# 检查依赖更新
mvn versions:display-dependency-updates
```

### 插件管理
```bash
# 查看插件信息
mvn help:describe -Dplugin=spring-boot

# 查看插件目标
mvn help:describe -Dplugin=spring-boot -Ddetail=true

# 检查插件更新
mvn versions:display-plugin-updates
```

## 🛠️ 项目信息查看

### 项目状态
```bash
# 查看Maven版本
mvn -version

# 查看项目信息
mvn help:describe -Dplugin=help

# 查看有效POM（合并后的配置）
mvn help:effective-pom

# 查看有效settings
mvn help:effective-settings

# 验证项目配置
mvn validate
```

### 代码质量检查
```bash
# 编译检查
mvn compiler:compile

# 查找编译错误
mvn clean compile -X

# 静态代码分析（需要配置相应插件）
mvn checkstyle:check
mvn pmd:check
mvn spotbugs:check
```

## 🚀 常用组合命令

### 开发阶段
```bash
# 快速启动开发
mvn clean compile spring-boot:run

# 完整测试
mvn clean test

# 快速打包（跳过测试）
mvn clean package -DskipTests

# 解决中文乱码的打包和运行
chcp 65001 && mvn clean package -DskipTests
```

### Java应用运行（解决中文乱码）
```bash
# Windows PowerShell中文乱码解决方案

# 方案1：设置控制台编码 + Java参数
chcp 65001
java -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -jar target\copilot-java-demo2-1.0.0-SNAPSHOT.jar

# 方案2：完整的Java启动参数
java -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -jar target\copilot-java-demo2-1.0.0-SNAPSHOT.jar

# 方案3：使用启动脚本（推荐）
.\start-utf8.ps1  # PowerShell脚本
.\start-utf8.bat  # 批处理脚本

# 方案4：Maven直接运行（开发时）
chcp 65001 && mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dfile.encoding=UTF-8"
```

### 部署准备
```bash
# 生产环境打包
mvn clean package -Pprod

# 完整构建验证
mvn clean verify

# 部署到远程仓库
mvn clean deploy
```

### 问题排查
```bash
# 详细日志模式
mvn clean compile -X

# 安静模式（只显示错误）
mvn clean compile -q

# 强制更新快照
mvn clean compile -U

# 离线模式
mvn clean compile -o
```

## 💡 实用技巧

### 性能优化
```bash
# 并行构建
mvn clean install -T 4

# 跳过不必要的阶段
mvn install -Dmaven.javadoc.skip=true -Dmaven.source.skip=true
```

### 环境配置
```bash
# 指定JDK版本
mvn clean compile -Djava.version=8

# 指定编码
mvn clean compile -Dproject.build.sourceEncoding=UTF-8

# 设置内存参数
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
mvn clean install
```

## 📁 项目相关路径

- **源码目录**: `src/main/java`
- **资源目录**: `src/main/resources`
- **测试目录**: `src/test/java`
- **编译输出**: `target/classes`
- **打包输出**: `target/*.jar`
- **本地仓库**: `~/.m2/repository` (Windows: `%USERPROFILE%\.m2\repository`)

## 🔗 常用参数说明

| 参数 | 说明 | 示例 |
|------|------|------|
| `-D` | 设置系统属性 | `-Dspring.profiles.active=dev` |
| `-P` | 激活Profile | `-Pdev,test` |
| `-T` | 并行线程数 | `-T 4` |
| `-X` | 详细调试信息 | `-X` |
| `-q` | 安静模式 | `-q` |
| `-U` | 强制更新 | `-U` |
| `-o` | 离线模式 | `-o` |

---

## 📚 更多信息

- [Maven官方文档](https://maven.apache.org/guides/)
- [Spring Boot Maven插件](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)
- [Flyway Maven插件](https://flywaydb.org/documentation/usage/maven/)

---

> 💡 **提示**: 在项目根目录执行这些命令，确保当前目录包含 `pom.xml` 文件。
