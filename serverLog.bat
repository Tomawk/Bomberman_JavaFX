@echo off

REM Change these two directories as needed
set JDKBIN="C:\prg\jdk8\bin"
set PROJECT_DIR=C:\prg\myapps\Bomberman

REM Compiling
%JDKBIN%\javac %PROJECT_DIR%\src\ServerLogAttivitaXML.java -cp %PROJECT_DIR%\build\classes -d %PROJECT_DIR%\build\classes

REM Running
cd %PROJECT_DIR%\build\classes\
%JDKBIN%\java ServerLogAttivitaXML

pause