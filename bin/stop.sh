#!/bin/sh
echo "Stopping cloud-func Application"
pid=`ps -ef | grep cloud-func.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
fi