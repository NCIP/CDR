echo off
echo REMARK!!! Do not answer at the '[y,n]' prompt. 'y' will be automatically replied.
IF %1.==. GOTO No1
GOTO No2
:No1
  set JAVA_OPTS=-XX:MaxPermSize=512m -XX:PermSize=768m -Xms1024m -Xmx2048m
  cmd /c echo y> "%temp%\answerTT99.tmp"
  grails upgrade ^< "%temp%\answerTT99.tmp" ^& del "%temp%\answerTT99.tmp" ^& grails run-app
GOTO End1
:No2
  IF "%~1" == "80" GOTO No3
  IF "%~1" == "9090" GOTO No3 
  IF "%~1" == "8080" GOTO No3 
  IF "%~1" == "8181" GOTO No3 
  IF "%~1" == "9191" GOTO No3
  ECHO Port Number: 80, 8080, 8181, 9090 or 9191 only
GOTO End1
:No3
  set JAVA_OPTS=-XX:MaxPermSize=512m -XX:PermSize=768m -Xms1024m -Xmx2048m
  cmd /c echo y> "%temp%\answerTT99.tmp"
  grails upgrade ^< "%temp%\answerTT99.tmp" ^& del "%temp%\answerTT99.tmp" ^& grails -Dserver.port=%1 run-app
GOTO End1
:End1