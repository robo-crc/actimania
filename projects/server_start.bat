@echo off
:: https://developers.google.com/appengine/docs/java/tools/maven
pushd ..\src\server
call ..\..\tools\maven\bin\mvn.bat appengine:devserver
popd