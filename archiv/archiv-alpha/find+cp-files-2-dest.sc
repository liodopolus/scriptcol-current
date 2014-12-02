#!/bin/sh
# find FILE and copies to DIR

CWD=`pwd`
LST=$1 # first Parameter to script
SRC="/movies/slackware/packages"

echo ""
echo "Source is <$SRC>"
echo ""

for i in `cat $LST` ; do find $SRC -iname "$i*" -exec cp '{}' . \; ; done



