#!/bin/sh
# postgresqldump cron daily script
#
#see: http://www.postgresql.org/docs/9.4/static/backup-dump.html
#
# dump db
#pg_dump dbname > outfile
#
# reload db
#psql dbname < infile
#
# dump large db
#pg_dump dbname | gzip > filename.gz
#
# reload
#gunzip -c filename.gz | psql dbname
#
#
# to solve cron-job dump-problems
#
# 1. see: http://stackoverflow.com/questions/17996957/fe-sendauth-no-password-supplied
# 2. see: http://stackoverflow.com/questions/11919391/postgresql-error-fatal-role-username-does-not-exist
#
# 1. to solve problem with client (pg_dump) connection to database edit ../9.3/data/pg_hba.conf change md5 to trust 
# 2. to solve problem with root role do: "sudo -u username createuser root"


# current dir
CWD=$(pwd)

# timestamp
DATE="`date +%Y-%b-%d`"

# target dir, where to put databases
TAR="/var/www/nginx/databases-pgsql"

# check tar dir
if [ ! -d $TAR ] ; then
	mkdir -p $TAR
	chown amorsql:amorsql $TAR
	chmod 775 $TAR
fi


# databases it is important to choose the right backup method!
PgsqlDB_Names="aikidokyritz-d7-pgsql mydjangosite"


# only for Postgresql Databases
for Database in $PgsqlDB_Names ; do
	sudo -u amorsql pg_dump $Database > $TAR/"$DATE"_"$Database"_PgsqlDB.sql
done


# remove backups older than 3 day's
# exec serveral times the command
#find $TAR -type f -name "*.sql" -mtime +2 -print0 -execdir rm -f '{}' \;
# exec one time the command 
find $TAR -type f -name "*.sql" -mtime +2 -print0 -execdir rm -f '{}' \+


