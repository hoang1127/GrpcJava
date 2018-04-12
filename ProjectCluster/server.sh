#!/bin/bash
#
# Start the server from the config file
#

export SVR_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Starting Server from ${SVR_HOME}"

echo server home = $SVR_HOME
JAVA_MAIN='gash.router.server.MessageApp'
JAVA_ARGS="$1"
echo -e "\n** config: ${JAVA_ARGS} **\n"
JAVA_TUNE='-server -Xms500m -Xmx1000m'

java ${JAVA_TUNE} -cp .:${SVR_HOME}/lib/'*':${SVR_HOME}/classes ${JAVA_MAIN} ${JAVA_ARGS} 
