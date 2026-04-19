md src\main\java	
rem Application\Library sources
md src\main\resources	
rem Application\Library resources
md src\main\filters	
rem Resource filter files
md src\main\webapp	
rem Web application sources
md src\test\java	
rem Test sources
md src\test\resources	
rem Test resources
md src\test\filters	
rem Test resource filter files
md src\it	
rem Integration Tests (primarily for plugins)
md src\assembly	
rem Assembly descriptors
md src\site	
rem Site
@echo off
@title 批处理判断文件夹是否存在

if not exist LICENSE.md (
echo A >LICENSE.md
) 
rem Project's license
if not exist NOTICE.md (
echo A >NOTICE.md	
) 
rem Notices and attributions required by libraries that the project depends on
if not exist README.md (
echo A >README.md	
) 
rem Project's readme