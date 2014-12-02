#!/bin/sh
# written by Jeffrey Scherling Sun Jun 17 14:00:18 CEST 2012
# backup partion table of gamma with sfdisk dump 

DEVLIST="sda"	# don't change, more than one are possible
#DEVLIST=$(sfdisk -s | grep dev | cut -b 6-8)	# all availlable devices
DATE="`date +%Y-%m-%d_%H-%M`"


echo ""
echo "Verifying Disk before Dump"
echo "---------------------------"
sfdisk -V 
echo "---------------------------"

echo "Dumping Device List: [yes/no ]" ; read ANS

if [ "$ANS" = "yes" ] ; then
	for DEV in $DEVLIST ; do 
	echo "Dumping Partition Table of Device: $DEV"
	RFILE="sfdisk-dump-gamma-$DEV-$DATE"
	DISK="/dev/$DEV"
	sfdisk -d $DISK > $RFILE
	done

elif [ "$ANS" = "no" ] ; then

       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi


# -
