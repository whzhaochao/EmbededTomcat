set projectPath=%cd%
for %%I in ( %cd%\TomcatDemo\WEB-INF\lib\*.jar) do call cpappend.bat %%I
set CLASSPATH=%CLASSPATH%;%cd%\TomcatDemo\WEB-INF\classes;
@echo CLASSPATH=%CLASSPATH%
@echo ===========================================
rem need start calss name 
SET CLSNAME= com.zhaochao.action.EmbededTomcat
@echo CLSNAME=%CLSNAME% 
java -cp %CLASSPATH%  %CLSNAME% %projectPath% 80  
pause