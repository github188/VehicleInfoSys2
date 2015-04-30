@echo off
setlocal
pushd %~dp0
cd ..
set root_path=%cd%
set mysql_home=%root_path%\mysql
%mysql_home%\bin\mysqld --install mysql_car  --defaults-file=%mysql_home%\my.ini
net start mysql_car

pushd %root_path%\script
call initData.bat

