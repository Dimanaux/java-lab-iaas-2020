#!/usr/bin/env bash

mvn clean package

echo 'Restart server...'

pgrep java | xargs kill -9
nohup java -jar javaiass-0.1.jar > log.txt &
EOF

echo 'End'