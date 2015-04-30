@echo off
pushd %~dp0
setlocal

cd ../db
pushd %cd%
call mergeSql.bat

cd ../../../../package-installer
del *.iss
del /f /q db\*.*
del /f /q script\*.*

cd apache-tomcat-6.0.39
pushd %cd%
echo 清空tomcat下的temp目录和work目录
set tomcat_path=%cd%

del %tomcat_path%\conf\server.xml

if exist %tomcat_path%\temp (rd /s /q %tomcat_path%\temp)
if exist %tomcat_path%\work (rd /s /q %tomcat_path%\work)
set app_dir=%tomcat_path%\webapps\ROOT
if exist %app_dir% (rd /s /q %app_dir%)
