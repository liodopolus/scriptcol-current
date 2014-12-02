#!/bin/sh
# written by Jeffrey Scherling Sun Jun 17 14:00:18 CEST 2012
# backup mbr of gamma with dd

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
	echo "Dumping Masterboot Record of Device: $DEV"
	RFILE="dd-mbr-gamma-$DEV-$DATE.img"
	DISK="/dev/$DEV"
	dd if=$DISK of=$RFILE bs=512 count=1
	done

elif [ "$ANS" = "no" ] ; then

       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi


# -
