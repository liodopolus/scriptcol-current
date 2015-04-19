#!/bin/bash
# upgrade slackware every day
. /media/gamma_system/update/package sync

# remove stamps older than 1 day +0
TAR="/root"

find $TAR -type f -name "cron-daily-slackware*" -mtime +0 -print0 -execdir rm -f '{}' \+

