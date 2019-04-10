#!/bin/bash
#rsync -aP --delete --exclude={.idea,sync.sh,.git,build,Insur.zip} . root@nebula.nubeslab.com:/root/Insur

rsync -aP --delete --exclude={.idea,sync.sh,.git,build,Insur.zip} . root@47.254.201.14:/root/Insur

