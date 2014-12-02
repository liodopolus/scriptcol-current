#!/bin/bash
# upgrade slackware time every day
# stop ntpd 
. /etc/rc.d/rc.ntpd stop

# set time manuallly
ntpd -q -g -x -n

# start ntpd 
. /etc/rc.d/rc.ntpd start

