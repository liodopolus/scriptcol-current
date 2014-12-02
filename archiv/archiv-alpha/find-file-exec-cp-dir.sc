#!/bin/sh
#---js---#
# find files and copy to target
# usage script + startpoint where begin to search

FILE="*.txz"
TAR="target"
SRC="." # default startpoint
SRC=$1
CWD=`pwd`
DIR=$CWD/$TAR

mkdir $TAR

find $SRC -iname $FILE -exec cp {} $DIR \;

echo -e "\n Searched Files in $TAR \n"

#---js---#

