@echo off
set JAVA_HOME=<JAVA PATH>
echo Setting JAVA_HOME to: %JAVA_HOME%
echo.
mvn clean test
