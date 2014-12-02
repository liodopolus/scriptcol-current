#!/bin/sh
# written by Jeffrey Scherling Wed May 12 16:22:55 CEST 2010
# find missed installed Slackware packages with FILELIST.TXT in /var/log/packages


if [ ! -e FILELIST.TXT ] ; then
	echo "------------------------"
	echo "FILELIST.TXT not present"
	echo "------------------------"
	wget http://rsync.slackware.pl/slackware/slackware64-current/FILELIST.TXT
fi

LOC="/var/log/packages"

#name and version
PKG=$(cat FILELIST.TXT | grep slackware64 | grep "txz.asc" | cut -d / -f 4- | sed -e s/.txz.asc$//)

rm Missed-PKG

for i in $PKG ; do 
	if [ ! -e $LOC/$i  ] ; then
		echo $i >> Missed-PKG
	fi
done 

echo "------------------------"
echo "See Missed-PKG"
echo "------------------------"

# -
