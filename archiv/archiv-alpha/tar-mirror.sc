#!/bin/sh
# written by Jeffrey Scherling Sun Jan  2 19:48:39 CET 2011
# tar mirror script

SRC=$1
DST=$2

DATE="`date +%Y-%m-%d_%H-%M`"

echo ""
echo "ATTENTION: You Copy all Files from $SRC to $DST ! [yes/no ]" ; read ANS
echo ""

if [ "$ANS" = "yes" ] ; then
       echo "Start Copying"

       ( cd $SRC ; tar -cSp --numeric-owner --atime-preserve -f - . ) | ( cd $DST ; tar -xSpv --atime-preserve -f - )

elif [ "$ANS" = "no" ] ; then

       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi

echo ""
echo "Copy from $SRC to $DST Finished."
echo ""


# -
