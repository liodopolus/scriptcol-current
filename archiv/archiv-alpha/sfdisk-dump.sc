#!/bin/sh
# written by Jeffrey Scherling Sat Sep 10 11:26:27 CEST 2011
# generate backup of partion tables with sfdisk dump 

# don't change, if you are not sure what you doing
# by hand
#DEVLIST="sda sdb sdc"
# all availlable devices
DEVLIST=$(sfdisk -s | grep dev | cut -b 6-8)
DATE="`date +%Y-%m-%d_%H-%M`"


echo ""
echo "Verifying Disks before Dump"
echo "---------------------------"
sfdisk -V 
echo "---------------------------"


echo "Dumping Device List: [yes/no ]" ; read ANS

if [ "$ANS" = "yes" ] ; then
	for DEV in $DEVLIST ; do 
	echo "Dumping Partition Table of Device: $DEV"
	RFILE="sfdisk-dump-$DEV-$DATE"
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
