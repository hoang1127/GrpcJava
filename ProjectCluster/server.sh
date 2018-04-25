#!/bin/bash
#
# Runserver with config file
#

export SVR_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Run Server from ${SVR_HOME}"

echo server home = $SVR_HOME
JAVA_APP='gash.router.server.MessageApp'
JAVA_ARG="$1"
echo -e "\n** config at: ${JAVA_ARG} **\n"
JAVA_SER='-server -Xms500m -Xmx1000m'

java ${JAVA_SER} -cp .:${SVR_HOME}/lib/'*':${SVR_HOME}/classes ${JAVA_APP} ${JAVA_ARG} 
