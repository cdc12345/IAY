@echo off
del .\lib\mcreator.jar>>NUL
if not exist mcreator.exe (
 echo Important files lost
 pause
 exit
)
call config.bat
.\jdk\bin\java.exe -javaagent:%agentPath%=safeMode --add-opens=java.base/java.lang=ALL-UNNAMED -cp ./lib/*;mcreator.exe net.mcreator.Launcher