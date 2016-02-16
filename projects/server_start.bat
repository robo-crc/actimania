@echo off
pushd ..\src\scripts
start start_mongo.bat
popd
pushd ..\src\server
call ..\..\tools\maven\bin\mvn.bat jetty:run
popd