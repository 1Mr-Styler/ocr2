#!/usr/bin/env bash

export JAVA_HOME=/root/.sdkman/candidates/java/current

echo "~~~~~~~~~~~~~~~~~~~~~  Running WUI Server"
./gradlew bootRun --debug-jvm -Dgrails.server.port=8080

