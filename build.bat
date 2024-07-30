@echo off

REM Call PowerShell script to perform the build
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0build.ps1"
