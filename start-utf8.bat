@echo off
:: 设置控制台代码页为UTF-8
chcp 65001 > nul

:: 设置Java系统属性解决中文乱码
set JAVA_OPTS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Duser.timezone=Asia/Shanghai

:: 启动应用
echo 启动Spring Boot应用...
echo 使用UTF-8编码，解决中文乱码问题
echo.

java %JAVA_OPTS% -jar target\copilot-java-demo2-1.0.0-SNAPSHOT.jar

pause
