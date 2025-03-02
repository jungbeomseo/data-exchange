#!/bin/bash
if [[ $# -ne 1 ]]; then
    echo "Usage: [SERVER_NAME = grpcServer | simpleServer]"
    exit
elif [[ $1 == "grpcServer" || $1 == "simpleServer" ]]; then
    mvn -pl $1 spring-boot:run
fi
