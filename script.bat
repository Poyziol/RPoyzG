@echo off
echo === Compilation ===

REM Compile tous les .java de src en une seule passe (beaucoup plus rapide)
set "SRC_LIST=%TEMP%\javac_sources.txt"
if exist "%SRC_LIST%" del /f /q "%SRC_LIST%"

for /R src %%f in (*.java) do (
    echo %%f>>"%SRC_LIST%"
)

javac -d bin -sourcepath src @"%SRC_LIST%"
set "JAVAC_ERROR=%ERRORLEVEL%"

if exist "%SRC_LIST%" del /f /q "%SRC_LIST%"

echo === Fin compilation (code %JAVAC_ERROR%) ===

IF %JAVAC_ERROR% NEQ 0 (
    echo Erreur de compilation.
    pause
    exit /b %JAVAC_ERROR%
)

echo === Execution ===
java -cp "bin;resources" Main
pause
