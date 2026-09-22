@echo off
chcp 65001 >nul
cd /d "%~dp0"
javac -encoding UTF-8 RenameTool.java
if errorlevel 1 goto compile_failed
java RenameTool
pause
exit /b 0
:compile_failed
echo Compilation failed. Please install a JDK and check javac.
pause
exit /b 1
