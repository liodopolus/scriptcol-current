#!/bin/sh
# find files older than days and remove them

# where
TAR="/tmp"
#TAR="/etc"

# yesterday is +1, day today is 0, tomorrow is -1 
#DAY="+2" # older than 3 day's
#DAY="+1" # older than 2 day's
#DAY="+0" # older than 1 day's
#DAY="1" # yesterday
#DAY="0" # today

# string to search
#NAM="*.sql"
#NAM="*.log"
NAM="*~"

# remove backups older than 3 day's
# exec serveral times the command
#find $TAR -type f -name "*.sql" -mtime +2 -print0 -execdir rm -f '{}' \;

# exec one time the command 
#find $TAR -type f -name $NAM -mtime $DAY -print0 -execdir rm -f '{}' \+

# ask for executing
find $TAR -type f -name $NAM -mtime $DAY -print0 -okdir rm -f '{}' \+

