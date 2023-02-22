call runcrud.bat
if "%ERRORLEVEL%" == "0" goto chrome
echo.
echo GRADLEW BUILD has errors - breaking work
goto fail

:chrome
echo Google Chrome web browser is starting...
"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
start http://localhost:8080/crud/v1/task/tasks

goto end

:fail
echo.
echo There were errors.

:end
echo.
echo Work is finished.