@echo off
TITLE The Takeout Server Launcher
echo ---------------------------------------------------
echo    THE TAKEOUT - SERVER LAUNCHER
echo ---------------------------------------------------
echo.

:: 1. Check for Java
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH.
    echo Please install Java 17 or higher.
    pause
    exit /b
)
echo [OK] Java found.

:: 2. Check for Maven
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Maven (mvn) is not installed or not in PATH!
    echo The server cannot start without Maven.
    echo.
    echo STEPS TO FIX:
    echo 1. Download Apache Maven from: https://maven.apache.org/download.cgi
    echo 2. Extract it to a folder (e.g. C:\maven)
    echo 3. Add the 'bin' folder to your System PATH variables.
    echo 4. Restart this script.
    echo.
    pause
    exit /b
)
echo [OK] Maven found.

:: 3. Run Server
echo.
echo Starting Spring Boot Application...
echo The server will run on http://localhost:9091
echo.
call mvn spring-boot:run

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] The server failed to start.
    echo Check the error messages above.
)

pause
