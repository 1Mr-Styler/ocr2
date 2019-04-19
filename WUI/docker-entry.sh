#!/usr/bin/env bash

export JAVA_HOME=/root/.sdkman/candidates/java/current

echo "~~~~~~~~~~~~~~~~~~~~~  Running WUI Server"
./gradlew bootRun -Dgrails.server.port=8080

