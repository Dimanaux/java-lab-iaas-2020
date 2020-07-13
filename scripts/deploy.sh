#!/usr/bin/env bash

mvn clean package

echo 'Starting server...'

mvn spring-boot:start

echo 'All good'