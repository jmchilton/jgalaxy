#!/bin/bash

cp target/*war $CATALINA_HOME/webapps/
tail -f $CATALINA_HOME/logs/catalina.out