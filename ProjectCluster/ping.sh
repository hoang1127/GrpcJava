#!/bin/bash
# Ping

export SVR_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Starting from: ${SVR_HOME} "

echo server home = $SVR_HOME

JAVA_APP='gash.router.app.DemoApp'

JAVA_ARG="localhost 5123"

JAVA_SER='-client -Djava.net.preferIPv4Stack=true'

java ${JAVA_SER} -cp .:${SVR_HOME}/lib/'*':${SVR_HOME}/classes ${JAVA_APP} ${JAVA_ARG} 
