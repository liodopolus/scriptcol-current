#!/bin/sh
# getting KDE4-Source
VERSION=0.7.4
WDIR=compiz-fusion-$VERSION
mkdir -p $WDIR
cd $WDIR
cat > scriptfile << !
lftp http://releases.compiz-fusion.org \\ 
lftp -c mirror -n $VERSION . 
!
lftp -f scriptfile
wait
rm scriptfile
echo "finished"
