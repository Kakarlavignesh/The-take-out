@echo off
TITLE The Takeout Server Launcher

echo.
echo =========================================================
echo   STARTING THE TAKEOUT SERVER...
echo   Please wait... The website will open automatically!
echo =========================================================
echo.

:: 1. Cleanup old processes
echo Stopping any existing servers...
taskkill /F /IM java.exe >nul 2>&1
timeout /t 2 /nobreak >nul

:: 2. Launch browser in parallel (wait 15s)
start "" cmd /c "timeout /t 15 >nul & start http://localhost:9091"

:: 3. Start Server (using robust setup script)
echo Entering server directory...
cd server

echo Running auto-setup script...
powershell -ExecutionPolicy Bypass -File "debug_run.ps1"

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] PowerShell script failed. Attempting fallback...
    mvn spring-boot:run
)

pause
