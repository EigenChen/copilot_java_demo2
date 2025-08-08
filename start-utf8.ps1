# PowerShell脚本：解决Java应用中文乱码问题
# 设置控制台编码为UTF-8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::InputEncoding = [System.Text.Encoding]::UTF8

# 设置控制台代码页为UTF-8
chcp 65001 | Out-Null

Write-Host "======================================" -ForegroundColor Green
Write-Host "启动Spring Boot应用 (UTF-8编码)" -ForegroundColor Green  
Write-Host "======================================" -ForegroundColor Green
Write-Host ""

# Java启动参数
$javaOpts = @(
    "-Dfile.encoding=UTF-8",
    "-Dconsole.encoding=UTF-8", 
    "-Duser.timezone=Asia/Shanghai",
    "-Dspring.profiles.active=dev"
)

# 启动应用
$jarFile = "target\copilot-java-demo2-1.0.0-SNAPSHOT.jar"

if (Test-Path $jarFile) {
    Write-Host "启动参数: $($javaOpts -join ' ')" -ForegroundColor Yellow
    Write-Host "JAR文件: $jarFile" -ForegroundColor Yellow
    Write-Host ""
    
    java $javaOpts -jar $jarFile
} else {
    Write-Host "错误：找不到JAR文件 $jarFile" -ForegroundColor Red
    Write-Host "请先运行: mvn clean package" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "按任意键退出..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
