@echo off
rem ----------------------------------------------------------------------------
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.
rem ----------------------------------------------------------------------------

@rem ----------------------------------------------------------------------------
@rem Maven start up batch script
@rem
@rem Required ENV vars:
@rem
@rem   JAVA_HOME - location of a JDK home dir
@rem   MAVEN_HOME - location of Maven home dir
@rem   MAVEN_OPTS - parameters passed to the Java VM when running Maven
@rem ----------------------------------------------------------------------------

@rem Begin all REM comments with '@rem ' to ensure they work properly on systems like MS-DOS
@rem CD to Maven's root dir so we can find this script in all cases

if "%MAVEN_SKIP_RC%"=="" (
  if exist "%USERPROFILE%\mavenrc_pre.cmd" call "%USERPROFILE%\mavenrc_pre.cmd"
)

@rem Find the drive letter of the script
set DRIVE=%~d0
@rem Get the short path name of the script
for %%I in ("%~dp0.") do set M2_HOME=%%~sI
@rem Change the current directory to the Maven root dir
cd /d "%M2_HOME%\.."

@rem To be changed to >nul in final release:
@rem echo "Using Maven home: %M2_HOME%"

if "%MAVEN_SKIP_RC%"=="" (
  if exist "%USERPROFILE%\mavenrc_post.cmd" call "%USERPROFILE%\mavenrc_post.cmd"
)

set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

if not defined JAVA_HOME goto findJavaFromPath
set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
if exist %JAVA_EXE% goto init
echo The JAVA_HOME environment variable is not defined correctly.
echo This environment variable is needed to run this program.
echo NB: JAVA_HOME should point to a JDK not a JRE
goto error

:findJavaFromPath
@rem Try to find java.exe from system path
set JAVA_EXE=java
where %JAVA_EXE% >NUL 2>NUL
if "%ERRORLEVEL%"=="0" goto init

echo The JAVA_HOME environment variable is not defined correctly.
echo This environment variable is needed to run this program.
echo NB: JAVA_HOME should point to a JDK not a JRE
goto error

:init
@rem Run the Java program
"%JAVA_EXE%" %MAVEN_OPTS% -classpath "%M2_HOME%\wrapper\maven-wrapper.jar" -Dmaven.multiModuleProjectDirectory="%M2_HOME%\.." %WRAPPER_LAUNCHER% %*

:end
@rem End of the script
exit /b %ERRORLEVEL%

:error
@rem If there was an error, display a message and exit
echo Error occurred during initialization of VM
exit /b 1
