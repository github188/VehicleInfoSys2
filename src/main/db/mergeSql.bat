@echo off
pushd %~dp0
type create_db.sql vehicle.sql data.sql>all.sql