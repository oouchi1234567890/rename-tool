@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist "RenameTool.jar" goto missing_jar
java -jar RenameTool.jar
set "result=%errorlevel%"
pause
exit /b %result%
:missing_jar
echo RenameTool.jar was not found.
pause
exit /b 1
