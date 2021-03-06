#!/bin/sh
# mysqldump cron daily script
# InnoDB are the prefered way, because of their performance

# current dir
CWD=$(pwd)

# timestamp
DATE="`date +%Y-%b-%d`"

# target dir, where to put databases
TAR="/var/www/nginx/databases"

# check tar dir
if [ ! -d $TAR ] ; then
	mkdir -p $TAR
fi


# databases it is important to choose the right backup method!
MyIsDB_Names="aikidokyritz"
InnoDB_Names="aikidokyritz-d7"


# only for MyIsam Databases
for Database in $MyIsDB_Names ; do
mysqldump -u root -pgfh37jkm --flush-logs --master-data=2 --databases $Database > $TAR/"$DATE"_"$Database"_MyIsDB.sql
done


# only for InnoDB Databases
for Database in $InnoDB_Names ; do
mysqldump -u root -pgfh37jkm --flush-logs --master-data=2 --single-transaction --databases $Database > $TAR/"$DATE"_"$Database"_InnoDB.sql
done


# remove backups older than 3 day's
# exec serveral times the command
#find $TAR -type f -name "*.sql" -mtime +2 -print0 -execdir rm -f '{}' \;
# exec one time the command 
find $TAR -type f -name "*.sql" -mtime +2 -print0 -execdir rm -f '{}' \+


