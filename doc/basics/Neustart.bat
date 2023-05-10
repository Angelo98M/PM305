@ECHO OFF
ping 127.0.0.1 -n 6 -w 1000 > nul
java -jar Game.jar