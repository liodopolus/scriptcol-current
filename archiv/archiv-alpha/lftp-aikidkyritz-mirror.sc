#!/bin/sh
# written by Jeffrey Scherling Sun Aug  1 17:55:46 CEST 2010
# mirror aikidokyritz.de to /var/www

CWD=$(pwd)
DATE="`date +%Y-%m-%d_%H-%M`"
LOC="/var/www"

# choose what to do, don't mirror and upload at same time
MIRROR="yes"
UPLOAD="no"
BACKUP="yes"


# check for right script location
if [ ! $CWD = $LOC ] ; then
	echo -e "\n MOVE THIS SCRIPT TO <$LOC> \n" 
	exit 1
fi

# ftp settings
DOM="aikidokyritz"
URL="ftp.$DOM.de"
USER="u776901"
PASSWD="8ef3d683"
PORT="21"

# backup settings
SRC="httpdocs" 
TAR="$LOC/htdocs"
BDIR="$DOM-backup"
NAME="$DATE-$DOM-jsb.tar.gz"



if [ "$MIRROR" = "yes" ] ; then

# check target directory and mirror content
if [ ! -d $TAR ] ; then
	mkdir -p $TAR
fi

cd $TAR

echo -e "---------------------------------------------------------------"
# set ftp:list-options -a in /etc/lftp.conf to get hidden files (.file)
lftp -c "open -e ll -u $USER,$PASSWD -p $PORT $URL/$SRC ; mirror -c -e -p --parallel=5"
echo -e "\n Mirror <$URL/$SRC>: \t [\033[1;35m  OK  \033[0m]\n"
echo -e "---------------------------------------------------------------"

cd $CWD

fi



if [ "$BACKUP" = "yes" ] ; then

# check backup directory and create backup
if [ ! -d ${BDIR} ] ; then
	mkdir -p $BDIR
fi

echo -e "---------------------------------------------------------------"
tar -cvzf $BDIR/$NAME $TAR
echo -e "\n Backup <$NAME>: \t [\033[1;35m  OK  \033[0m]\n"
echo -e "---------------------------------------------------------------"

fi



if [ "$UPLOAD" = "yes" ] ; then

# check source directory and upload content
if [ ! -d $TAR ] ; then
	echo "ERROR: DIRECTORY NOT AVAILABLE"
	exit 1
fi

cd $TAR

echo -e "---------------------------------------------------------------"
# set ftp:list-options -a in /etc/lftp.conf to get hidden files (.file)
lftp -c "open -u $USER,$PASSWD -p $PORT $URL/$SRC ; mirror -c -e -p -R --parallel=5"
echo -e "\n Upload <$URL/$SRC>: \t [\033[1;35m  OK  \033[0m]\n"
echo -e "---------------------------------------------------------------"

cd $CWD

fi



# permissions and umask
chown -R jeff:apache $TAR
chmod -R 775 $TAR
chmod 755 $TAR/sites/all
chmod 755 $TAR/sites/default
chmod 644 $TAR/sites/default/settings.php

# chmod 775
#SET1=`cat << EOF
#css
#fivestar
#images
#js
#playlists
#EOF`

#for i in $SET1 ; do 
#	chmod 775 $TAR/sites/default/files/$i
#done


# chmod 777
#chmod 777 $TAR/sites/default/files
#chmod -R 777 $TAR/tmp

#SET2=`cat << EOF
#backup_migrate
#benutzerbilder
#color
#ctools
#fotos
#imagecache
#languages
#EOF`

#for i in $SET2 ; do 
#	chmod 777 $TAR/sites/default/files/$i
#done


# -
