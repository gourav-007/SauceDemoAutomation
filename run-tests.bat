@echo off
set JAVA_HOME=C:\Users\goura\scoop\apps\openjdk17\current
echo Setting JAVA_HOME to: %JAVA_HOME%
echo.
mvn clean test
