#!/bin/sh
#---js---#
# backup boot and etc slackware64-current

DIR="/boot /etc" # backup directories 

#CWD=`pwd`

TAR="/mnt/movies/slackware64-current/configs"

DATE="`date +%Y-%m-%d--%H-%M`"

for i in $DIR ; do
	#( echo $i ; ls -1 $i ) # only for testing
	( tar cvzf "$TAR/backup-`basename $i`-$DATE.tar.gz" $i )
done

echo -e "\n Backup of $DIR in $TAR finished \n"


#---js---#


