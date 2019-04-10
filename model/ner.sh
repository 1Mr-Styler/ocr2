#!/bin/bash
scriptdir="/model"


export JAVA_HOME=/root/.sdkman/candidates/java/current
source ~/.bashrc
$JAVA_HOME/bin/java -mx3g -cp "$scriptdir/stanford-ner.jar:$scriptdir/lib/*" edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier $scriptdir/classifiers/ner-model.ser.gz -textFile $scriptdir/pred.txt -outputFormat inlineXML
