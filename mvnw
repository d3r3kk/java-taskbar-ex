#!/bin/sh
# ----------------------------------------------------------------------------
#  Licensed to the Apache Software Foundation (ASF) under one or more
#  contributor license agreements.  See the NOTICE file distributed with
#  this work for additional information regarding copyright ownership.
#  The ASF licenses this file to You under the Apache License, Version 2.0
#  (the "License"); you may not use this file except in compliance with
#  the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
# ----------------------------------------------------------------------------

##############################################################################
# Maven start up script for UN*X
#
# Environment Variable Prequisites
#
#   JAVA_HOME       Must point at your Java Development Kit installation.
#
#   MAVEN_HOME      (Optional) Maven's installation directory.
#
#   MAVEN_OPTS      (Optional) Java runtime options used when Maven is executed.
#                   e.g. to debug Maven itself, use
#                     set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
#
#   MAVEN_SKIP_RC   (Optional) Flag to disable loading of mavenrc files.
#                   Set to 'true' to skip loading of /etc/mavenrc and ~/.mavenrc
#
##############################################################################

if [ -z "$MAVEN_SKIP_RC" ] ; then

  if [ -f /etc/mavenrc ] ; then
    . /etc/mavenrc
  fi

  if [ -f "$HOME/.mavenrc" ] ; then
    . "$HOME/.mavenrc"
  fi

fi

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false
mingw=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  MINGW* )
    mingw=true
    ;;
  Darwin* )
    darwin=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$MAVEN_HOME" ] && MAVEN_HOME=`cygpath --unix "$MAVEN_HOME"`
  [ -n "$MAVEN_OPTS" ] && MAVEN_OPTS=`cygpath --unix "$MAVEN_OPTS"`
fi

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
  if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
    # IBM's JDK on AIX uses strange locations for the executables
    JAVACMD="$JAVA_HOME/jre/sh/java"
  else
    JAVACMD="$JAVA_HOME/bin/java"
  fi
  if [ ! -x "$JAVACMD" ] ; then
    echo "Error: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
    echo "Please set the JAVA_HOME variable in your environment to match the" >&2
    echo "location of your Java installation." >&2
    exit 1
  fi
else
  JAVACMD="`which java`"
  if [ -z "$JAVACMD" ] ; then
    JAVACMD=java
  fi
fi

# Determine the location of the Maven installation
if [ -z "$MAVEN_HOME" ] ; then
  ## resolve links - $0 may be a link to mvn's home
  PRG="$0"

  # need this for relative symlinks
  while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
      PRG="$link"
    else
      PRG=`dirname "$PRG"`/"$link"
    fi
  done

  SAVED="`pwd`"
  cd "`dirname \"$PRG\"`/.." >&-
  MAVEN_HOME="`pwd -P`"
  cd "$SAVED" >&-

  # make sure MAVEN_HOME is set correctly so that mvnw can execute it correctly
  export MAVEN_HOME

  # echo Using MAVEN_HOME: $MAVEN_HOME
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$MAVEN_HOME" ] && MAVEN_HOME=`cygpath --unix "$MAVEN_HOME"`
fi

CLASSWORLDS_JAR="$MAVEN_HOME/boot/plexus-classworlds-*.jar"
CLASSWORLDS_LAUNCHER="org.codehaus.plexus.classworlds.launcher.Launcher"

if [ "$MAVEN_DEBUG" = "true" ] ; then
  MAVEN_OPTS="$MAVEN_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=$MAVEN_DEBUG_PORT"
fi

# For Darwin, add Apple specific Java location
if $darwin; then
  if [ -z "$JAVA_VERSION" ] ; then
    JAVA_VERSION="CurrentJDK"
  fi
  if [ -z "$JAVA_HOME" ] ; then
    JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home"
  fi
  export JAVA_HOME
fi

# For NonStop Kernel (Tandem), add Java location
if $nonstop; then
  if [ -z "$JAVA_HOME" ]; then
    if [ -d "/usr/tandem/nssjava/jdk7" ]; then
      JAVA_HOME="/usr/tandem/nssjava/jdk7"
    else
      JAVA_HOME="/usr/tandem/nssjava/jdk6"
    fi
  fi
  export JAVA_HOME
fi

WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

exec "$JAVACMD" \
  $MAVEN_OPTS \
  -classpath "$MAVEN_HOME/wrapper/maven-wrapper.jar" \
  "-Dmaven.multiModuleProjectDirectory=$MAVEN_HOME" \
  $WRAPPER_LAUNCHER "$@"
