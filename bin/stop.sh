#!/bin/sh
echo stopping application cloud-func
pid=`ps -ef | grep cloud-func.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
else
   echo application already stop
fi
