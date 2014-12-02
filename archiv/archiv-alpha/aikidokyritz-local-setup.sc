#!/bin/sh
# written by Jeffrey Scherling Thu May 13 17:52:44 CEST 2010
# copy httpdocs to htdocs local an set permissions

STEP1="yes"

SRC="/mnt/movies/Aikidokyritz/Backups/aikidokyritz/httpdocs"
TAR="/srv/www/htdocs"

if [ "$STEP1" = "yes" ] ; then
 if [ -d $SRC ] ; then
	cp -r $SRC/.* $TAR
	chgrp -R users $TAR
 else 
	echo "---------"
	echo "No Source"
	echo "---------"
	exit 1
 fi
	echo "---------------"
	echo "Setup Complete "
	echo "---------------"
fi

# -
