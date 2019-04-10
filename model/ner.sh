#!/bin/bash
scriptdir=`dirname $0`

java -mx3g -cp "$scriptdir/stanford-ner.jar:$scriptdir/lib/*" edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier $scriptdir/classifiers/ner-model.ser.gz -textFile $1 -outputFormat inlineXML
