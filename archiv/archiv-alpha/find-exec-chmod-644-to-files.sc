#!/bin/sh
#---js---#
# find files and set Permissions for the given Extensions and Directory 
# usage script + startpoint where begin to search

# Extensions
FILE="*.txz *.tar.gz *.tar.bz2 *.tgz"

# Permissions
PER="644"
OWN="root:users"

# Startpoint
START="$1"
echo -e "\n Startpoint is [ $START ]\n"

for i in $FILE ; do
	( find $START -iname $i -exec chmod $PER {} \; )
 	wait
	( find $START -iname $i -exec chown $OWN {} \; )
 	wait
done

echo -e "\n Permissions to [ $PER ] and Ownership to [ $OWN ] for all Files with Expression [ $FILE ] \n"

#---js---#

