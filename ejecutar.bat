@echo off
setlocal
cd /d "%~dp0"
set "CP=target\classes;%USERPROFILE%\.m2\repository\com\formdev\flatlaf\3.7.2\flatlaf-3.7.2.jar"
start "" javaw -cp "%CP%" Presentacion.Inicio
endlocal

