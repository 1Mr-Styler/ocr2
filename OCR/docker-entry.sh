#!/usr/bin/env bash

export JAVA_HOME=/root/.sdkman/candidates/java/current

echo "~~~~~~~~~~~~~~~~~~~~~  Running OCR Server\n"
cd /apps/ocr #&& ./grailsw run --debug-jvm --port=8090
./gradlew bootRun --debug-jvm  -Dgrails.server.port=8090

