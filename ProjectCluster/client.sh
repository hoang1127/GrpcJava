#!/bin/bash
# Run client 

export SVR_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "RunClient from ${SVR_HOME}"

echo server home = $SVR_HOME

JAVA_APP='gash.router.app.ClientApp'
JAVA_ARG="localhost 5123"
JAVA_SER='-client -Djava.net.preferIPv4Stack=true'

java ${JAVA_SER} -cp .:${SVR_HOME}/lib/'*':${SVR_HOME}/classes ${JAVA_APP} $1
