# Java进程管理命令手册

## 📋 查询Java进程

### Windows系统

#### 1. jps命令（推荐 - JDK自带工具）
```powershell
# 显示所有Java进程
jps

# 显示详细信息（包含主类名和参数）
jps -l

# 显示JVM参数
jps -v

# 显示传递给main方法的参数
jps -m

# 过滤特定应用
jps -l | findstr -i "spring\|copilot\|suntek"
```

#### 2. tasklist命令
```powershell
# 查看所有java.exe进程
tasklist | findstr java

# 查看详细信息
tasklist /fi "imagename eq java.exe" /fo table

# 显示命令行参数
wmic process where "name='java.exe'" get ProcessId,CommandLine
```

#### 3. PowerShell
```powershell
# 查看Java进程
Get-Process | Where-Object {$_.ProcessName -eq "java"}

# 显示更多信息
Get-Process java | Format-Table Id,ProcessName,StartTime,CPU

# 显示详细信息
Get-WmiObject Win32_Process | Where-Object {$_.Name -eq "java.exe"} | Select-Object ProcessId,CommandLine
```

#### 4. 通过端口查找进程
```powershell
# 查看指定端口被哪个进程占用
netstat -ano | findstr :8888

# 根据PID查找对应进程
tasklist /fi "PID eq 进程ID"
```

### Linux/Mac系统

#### 1. jps命令
```bash
# 显示所有Java进程
jps

# 显示详细信息
jps -l

# 显示JVM参数
jps -v

# 过滤特定应用
jps -l | grep -E "(spring|copilot|suntek)"
```

#### 2. ps命令
```bash
# 查看Java进程
ps aux | grep java

# 只显示进程ID和命令
ps -ef | grep java | grep -v grep

# 显示详细的命令行
ps -ef | grep java | head -5
```

#### 3. pgrep命令
```bash
# 查找Java进程ID
pgrep java

# 显示进程ID和名称
pgrep -l java

# 查找特定Java进程
pgrep -f "spring-boot"
```

## 🔍 识别Spring Boot应用进程

### 通过进程名识别
Spring Boot应用的进程名通常显示为：
- `org.springframework.boot.loader.JarLauncher`（打包后运行）
- `org.suntek.CopilotJavaDemo2Application`（直接运行主类）
- 包含`spring-boot-maven-plugin`（Maven运行）

### 通过命令行参数识别
查找包含以下特征的进程：
- 包含项目路径
- 包含`-Dspring.application.name=应用名`
- 包含`spring-boot:run`

### 通过端口识别
```powershell
# Windows
netstat -ano | findstr :8888

# Linux/Mac
lsof -i :8888
netstat -tlnp | grep :8888
```

## 🔄 实时监控Java进程

### 方法1：启动前后对比
```powershell
# Windows
jps -l > before.txt
# 启动应用
mvn spring-boot:run
# 新开终端
jps -l > after.txt
fc before.txt after.txt
```

```bash
# Linux/Mac
jps -l > before.txt
# 启动应用
mvn spring-boot:run
# 新开终端
jps -l > after.txt
diff before.txt after.txt
```

### 方法2：定时查看
```powershell
# Windows - 每5秒显示一次Java进程
while ($true) { Clear-Host; jps -l; Start-Sleep 5 }
```

```bash
# Linux/Mac - 每5秒显示一次Java进程
watch -n 5 'jps -l'
```

## ⚡ 终止Java进程

### Windows系统

#### 1. 根据进程ID终止
```powershell
# 正常终止
taskkill /PID 进程ID

# 强制终止
taskkill /PID 进程ID /F
```

#### 2. 根据进程名终止
```powershell
# 终止所有java进程
taskkill /IM java.exe /F

# 终止特定名称的java进程（谨慎使用）
wmic process where "CommandLine like '%spring-boot%'" delete
```

### Linux/Mac系统

#### 1. 根据进程ID终止
```bash
# 正常终止
kill 进程ID

# 强制终止
kill -9 进程ID
```

#### 2. 根据进程名终止
```bash
# 终止所有java进程
pkill java

# 终止特定的java进程
pkill -f "spring-boot"

# 使用killall（某些系统）
killall java
```

## 🛠️ Maven Spring Boot 特定命令

### 启动和停止
```bash
# 启动应用
mvn spring-boot:run

# 后台启动（Linux/Mac）
nohup mvn spring-boot:run &

# 停止应用（Ctrl+C或找到进程ID后kill）
```

### 调试模式启动
```bash
# 启动调试模式
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 📊 进程信息查看

### 查看JVM内存使用
```powershell
# Windows
jstat -gc 进程ID

# 显示GC信息
jstat -gcutil 进程ID 1s
```

### 查看线程信息
```bash
# 显示线程堆栈
jstack 进程ID

# 显示线程数
jstack 进程ID | grep "java.lang.Thread.State" | wc -l
```

## 💡 实用技巧

### 1. 快速识别Spring Boot应用
```powershell
# Windows
jps -v | findstr "spring.application.name"

# Linux/Mac
jps -v | grep "spring.application.name"
```

### 2. 查看应用启动时间
```powershell
# Windows
wmic process where "ProcessId=进程ID" get CreationDate

# Linux/Mac
ps -o lstart= -p 进程ID
```

### 3. 监控应用资源使用
```bash
# 显示CPU和内存使用
top -p 进程ID

# 只显示特定进程
htop -p 进程ID
```

## ⚠️ 注意事项

1. **使用jps命令需要JDK环境**
2. **强制终止进程可能导致数据丢失**
3. **生产环境请谨慎操作**
4. **建议先尝试正常关闭（Ctrl+C）**
5. **删除进程前确认是否为目标应用**

## 🔗 相关文档

- [JDK工具参考](https://docs.oracle.com/javase/8/docs/technotes/tools/)
- [Spring Boot Maven插件](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)
- [JPS命令详解](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jps.html)

---

> 💡 **提示**: 建议将`jps`作为首选工具，它提供最准确的Java进程信息。在生产环境中操作进程时要格外小心。
