@echo off
setlocal
pushd %~dp0

set script_path=%~dp0

rem 返回根目录
cd ..
set app_root=%cd%
pushd %app_root%

set java_home=
set JRE_HOME=%app_root%\jre
set catalina_home=%app_root%\tomcat
call %catalina_home%\bin\startup.bat

pushd %app_root%\server
call run.bat