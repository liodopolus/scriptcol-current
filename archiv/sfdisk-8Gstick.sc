#!/bin/sh
# written by Jeffrey Scherling Tue Mar  5 23:34:14 CET 2013
# generate partions on sdx = 8Gstick (USB-Stick) 8 GB
#
# Howto für die Berechnung von Gigabyte in Cylinder für sfdisk
#
#Blöcke sollen gerundet sein, deshalb auch mit Blockgrößen rechnen!
#
#USB-Stick
#4 Gb = 4 x 1000^2 = 4.000.000 blöcke x 1024 = 4 096 000 000 byte
#4 096 000 000 byte / 252 heads / 62 sectors / 512 byte / sectoren = 512 cylinder
#
#Festplatten
#40 Gb = 40 x 1000^2 = 40.000.000 blöcke x 1024 = 40 960 000 000 byte
#40 960 000 000 byte / 16065 / 512 = 4980 cylinder
#40 Gb = 4979,76 cylinder ~ 4980 cylinder 
#
#Bei den head und sectors die Werte von sfdisk -l /dev/sdx benutzen.


# don't change, if you are not sure what you doing
DISK="8Gstick"
DEV="/dev/sdb"
DATE="`date +%Y-%m-%d_%H-%M`"
RFILE1="sfdisk-dump-tmp-$DISK-$DATE"
RFILE2="sfdisk-dump-$DISK-$DATE"

echo "----------------------------------------------------------------------"
sfdisk -l $DEV
echo "----------------------------------------------------------------------"

echo ""
echo "DUMPING: Creating Temporary File for Restoring"
echo ""
sfdisk -d $DEV > $RFILE1


echo ""
echo "ATTENTION: You Create New Partion Table on $DEV ! [yes/no ]" ; read ANS
echo ""

if [ "$ANS" = "yes" ] ; then
       echo "Creating new Patition Table"

sfdisk  $DEV << EOF
0	1021	83	*
EOF
sfdisk -V $DEV

echo ""
echo "DUMPING: Creating File for Restoring"
echo ""
sfdisk -d $DEV > $RFILE2


elif [ "$ANS" = "no" ] ; then

       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi


# -
