#!/bin/bash
# check existense of nginx.pid
if [ -e "/var/run/nginx.pid" ] ; then
exit 0
else
echo "Subject: Nginx not running" | sendmail jeffrey.scherling@gmail.com
exit 1
fi
