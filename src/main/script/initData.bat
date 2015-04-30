@echo off
setlocal
pushd %~dp0

cd ..
set root_path=%cd%
set mysql_home=%root_path%\mysql
%mysql_home%\bin\mysql.exe -hlocalhost -uroot -pAst4HS<%root_path%\db\all.sql