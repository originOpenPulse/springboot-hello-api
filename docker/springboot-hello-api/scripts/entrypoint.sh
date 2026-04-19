#!/bin/bash

/bin/sh -c "export LD_LIBRARY_PATH=.:/app/:$LD_LIBRARY_PATH; java -agentlib:sg ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar springboot-hello-api-server.jar"
#java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar springboot-hello-api-server.jar
#java -jar springboot-hello-api-server.jar
