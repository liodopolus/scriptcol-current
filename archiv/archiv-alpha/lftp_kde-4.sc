#!/bin/sh
# getting KDE4-Source
VERSION=4.0.72
WDIR=kde-$VERSION
mkdir -p $WDIR
cd $WDIR 
cat > scriptfile << !
lftp ftp://ftp.fu-berlin.de/pub/unix/X11/gui/kde/unstable \\ 
lftp -c mirror -n $VERSION . 
!
lftp -f scriptfile
wait
rm scriptfile
echo "finished"
