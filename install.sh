#!/bin/bash

cp target/classes/com /usr/local/tomcat/webapps/ROOT/WEB-INF/classes -rf
cp log4j.properties /usr/local/tomcat/webapps/ROOT/WEB-INF/config -rf
