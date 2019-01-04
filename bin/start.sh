#!/bin/sh
sh ./stop.sh
APP_NAME=cloud-func.jar
export BIN_PATH=$(cd `dirname $0`;pwd)
echo BIN_PATH:[$BIN_PATH]
cd $BIN_PATH
nohup java -jar $APP_NAME > server.log 2>1&
