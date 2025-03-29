@echo off
cls
if exist .\lib\mcreator.jar del .\lib\mcreator.jar>NUL
if not exist mcreator.exe (
 echo Important files lost
 pause
 exit
)
goto config

:config
if exist config.bat call config.bat


:direct
set JAVA_CORE_ARGS=-javaagent:%agentPath% --add-opens=java.base/java.lang=ALL-UNNAMED --class-path ./lib/*;mcreator.exe
echo %JAVA_PATH% %JAVA_CORE_ARGS%%addition% %JAVA_ARGS% net.mcreator.Launcher
%JAVA_PATH% %JAVA_CORE_ARGS%%addition% %JAVA_ARGS% net.mcreator.Launcher
if "%DEBUG%"=="true" pause
exit