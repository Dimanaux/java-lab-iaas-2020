#!/usr/bin/env bash

cd ..
mvn clean package

echo 'Starting server...'

mvn spring-boot:start

echo 'All good'