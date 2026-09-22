@echo off
cd /d "%~dp0"
javac --release 8 -encoding UTF-8 RenameTool.java
if errorlevel 1 goto build_failed
jar cfe RenameTool.jar RenameTool RenameTool.class
if errorlevel 1 goto build_failed
echo Created RenameTool.jar
exit /b 0
:build_failed
echo Build failed. Please install a JDK version 9 or newer.
exit /b 1
