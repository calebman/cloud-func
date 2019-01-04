#!/bin/sh
nohup java -jar /home/app/application/cloud-func.jar > /home/app/application/server.log 2>1&
tail -f /home/app/application/server.log
