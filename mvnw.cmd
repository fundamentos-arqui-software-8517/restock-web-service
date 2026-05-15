@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file to
@REM you under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET "BASE_DIR=%~dp0")

@SET MAVEN_PROJECTBASEDIR=%BASE_DIR%
@IF NOT "%MAVEN_BASEDIR%"=="" SET "MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%"

@SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@SET DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"

@FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties") DO (
    @IF "%%A"=="wrapperUrl" SET DOWNLOAD_URL=%%B
)

@IF EXIST %WRAPPER_JAR% (
    @SET MVNW_VERBOSE=false
    @IF NOT "%MVNW_VERBOSE%"=="false" (
        @ECHO "Found %WRAPPER_JAR%"
    )
) ELSE (
    @IF NOT "%MVNW_REPOURL%"=="" SET DOWNLOAD_URL="%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
    @IF "%MVNW_VERBOSE%"=="true" (
        @ECHO Downloading from: %DOWNLOAD_URL%
    )
    powershell -Command "&{"^
		"$webclient = new-object System.Net.WebClient;"^
		"if (-not ([string]::IsNullOrEmpty('%MVNW_USERNAME%') -and [string]::IsNullOrEmpty('%MVNW_PASSWORD%'))) {"^
		"$webclient.Credentials = new-object System.Net.NetworkCredential('%MVNW_USERNAME%', '%MVNW_PASSWORD%');"^
		"}"^
		"[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient.DownloadFile('%DOWNLOAD_URL%', '%WRAPPER_JAR%')"^
		"}"
    IF "%MVNW_VERBOSE%"=="true" (
        @ECHO Finished downloading %WRAPPER_JAR%
    )
)

@SET MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
@SET "JAVA_HOME=%JAVA_HOME%"

@IF "%JAVA_HOME%"=="" (
  @FOR /F "usebackq tokens=1,2 delims==" %%A IN (`WMIC DATAFILE WHERE "Name='%SystemRoot%\\System32\\java.exe'" GET Version /VALUE 2>NUL`) DO SET "JAVA_HOME=%%B"
  @IF NOT "%JAVA_HOME%"=="" @FOR /F "usebackq delims=" %%A IN (`WHERE java`) DO @SET "MAVEN_JAVA_EXE=%%A"
)

@IF "%JAVA_HOME%"=="" (
  @ECHO Error: JAVA_HOME not found in your environment. >&2
  @ECHO Please set the JAVA_HOME variable in your environment to match the >&2
  @ECHO location of your Java installation. >&2
  @GOTO error
)

@SET MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"

@IF NOT EXIST %MAVEN_JAVA_EXE% (
  @ECHO Error: JAVA_HOME is set to an invalid directory: %JAVA_HOME% >&2
  @ECHO Please set the JAVA_HOME variable in your environment to match the >&2
  @ECHO location of your Java installation. >&2
  @GOTO error
)

@SET MVN_CMD="%MAVEN_JAVA_EXE%" ^
  %MAVEN_OPTS% ^
  %MAVEN_DEBUG_OPTS% ^
  -classpath %WRAPPER_JAR% ^
  "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" ^
  %WRAPPER_LAUNCHER% %MAVEN_CONFIG% %*

@%MVN_CMD%
@IF ERRORLEVEL 1 GOTO error
@GOTO end

:error
SET ERROR_CODE=1

:end
@ENDLOCAL & SET ERROR_CODE=%ERROR_CODE%

@IF NOT "%MAVEN_SKIP_RC%"=="" GOTO skipRcPost
@IF EXIST "%USERPROFILE%\mavenrc_post.bat" CALL "%USERPROFILE%\mavenrc_post.bat"
:skipRcPost

@cmd /C EXIT /B %ERROR_CODE%
