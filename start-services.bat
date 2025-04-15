@echo off
setlocal

:: Paths to your services
set "EUREKA_PATH=C:\Users\admin\Documents\Workspaces\java\chat_system\eureka-server"
set "CONFIG_PATH=C:\Users\admin\Documents\Workspaces\java\chat_system\config-server"
set "API-GATEWAY=C:\Users\admin\Documents\Workspaces\java\chat_system\api-gateway"

:: Start Eureka Server in a new command window
echo Starting Eureka Server...
start "Eureka Server" cmd /k "cd /d %EUREKA_PATH% && mvn spring-boot:run"

timeout /t 5 /nobreak >nul

echo Starting Config Service...
start "Config Service" cmd /k "cd /d %CONFIG_PATH% && mvn spring-boot:run"

timeout /t 5 /nobreak >nul

echo Starting API GATEWAY Service...
start "API GATEWAY" cmd /k "cd /d %CONFIG_PATH% && mvn spring-boot:run"

endlocal
