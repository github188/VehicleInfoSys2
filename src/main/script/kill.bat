@echo off
setlocal
pushd %~dp0

netstat.exe -ano |find "8081"|find "LISTENING">kill.txt
netstat.exe -ano |find "6616"|find "SYN_SENT">>kill.txt
for /f "tokens=5" %%a in (1.txt) do (taskkill /f /t /pid %%a)

cd ..
set app_root=%cd%
wmic process where "commandline like '%%%app_root:\=\\%%%'" get Caption,Handle.|find ".exe">1.txt
for /f "tokens=1,2" %%a in (1.txt) do (
if "%%a" neq "unins000.exe" ( taskkill /f /t /pid %%b)
)

del kill.txt
del 1.txt