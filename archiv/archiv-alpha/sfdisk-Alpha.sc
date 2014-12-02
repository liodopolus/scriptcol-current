#!/bin/sh
# written by Jeffrey Scherling Sat Sep 10 11:26:27 CEST 2011
# generate partions on sda = Alpha the same like Beta

# don't change, if you are not sure what you doing
DISK="Alpha"
DEV="/dev/sda"
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
-	6225	7	*
-	4980	83	-
-	1245	83	-
-	-	5	-
-	12450	83	-
-	24899	83	-
-	12450	83	-
-	12450	83	-
-	12450	83	-
-	-	83	-
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
