#!/bin/bash
# To the server from supplied config file. Test for ping

export SVR_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Starting from: ${SVR_HOME} "

echo server home = $SVR_HOME

JAVA_MAIN='gash.router.app.DemoApp'

JAVA_ARGS="localhost 4167"
JAVA_TUNE='-client -Djava.net.preferIPv4Stack=true'

java ${JAVA_TUNE} -cp .:${SVR_HOME}/lib/'*':${SVR_HOME}/classes ${JAVA_MAIN} ${JAVA_ARGS} 
