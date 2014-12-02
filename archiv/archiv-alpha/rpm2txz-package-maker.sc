#!/bin/sh
# --- js ---#
# create *.txz package from *.rpm one
# usage scrip + file enter

DIR=rpm2txzconvert # where all things happen see PKG 
FILE=$1
BASENAME=`basename $FILE .rpm`

CWD=$(pwd)
TMP=${TMP:-/tmp}
PKG=$TMP/$DIR

rm -r $PKG
mkdir $PKG
cp $FILE $PKG
cd $PKG

rpm2cpio $FILE | cpio --extract --make-directories
rm $FILE

find $PKG | xargs file | grep -e "executable" -e "shared object" | grep ELF \
  | cut -f 1 -d : | xargs strip --strip-unneeded 2> /dev/null

cd $PKG
/sbin/makepkg -l y -c n $TMP/$BASENAME-rpm2txz-js.txz

mv $TMP/$BASENAME-rpm2txz-js.txz $CWD

cd $CWD
 
echo "Package repacked"

ls 

# --- js ---#

