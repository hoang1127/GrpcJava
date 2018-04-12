#!/bin/bash
# Run client from a supplied config file

export SVR_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Start Client from ${SVR_HOME}"

echo server home = $SVR_HOME

JAVA_MAIN='gash.router.app.ClientApp'
JAVA_ARGS="localhost 4167"
JAVA_TUNE='-client -Djava.net.preferIPv4Stack=true'

java ${JAVA_TUNE} -cp .:${SVR_HOME}/lib/'*':${SVR_HOME}/classes ${JAVA_MAIN} $1
