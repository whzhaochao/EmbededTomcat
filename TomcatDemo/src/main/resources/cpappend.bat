rem ---------------------------------------------------------------------------
rem Append to CLASSPATH
rem
rem $Id: cpappend.bat 758256 2009-03-25 13:43:00Z mturk $
rem ---------------------------------------------------------------------------
 
rem Process the first argument
if ""%1"" == """" goto end
set CLASSPATH=%CLASSPATH%;%1
shift
 
rem Process the remaining arguments
:setArgs
if ""%1"" == """" goto doneSetArgs
set CLASSPATH=%CLASSPATH% %1
shift
goto setArgs
:doneSetArgs
:end