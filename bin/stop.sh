#!/bin/sh
echo stopping application cloud-func
pid=`ps -ef | grep cloud-func.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
   echo application stop success
else
   echo application already stop
fi
