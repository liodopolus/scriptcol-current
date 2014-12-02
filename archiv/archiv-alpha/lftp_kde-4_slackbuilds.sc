#!/bin/sh
# getting KDE4-Slackbuilds
VERSION=kde
WDIR=kde-$VERSION
cat > scriptfile << !
lftp http://kde4.rlworkman.net/source \\ 
lftp -c mirror -n $VERSION . 
!
lftp -f scriptfile
wait
rm scriptfile
echo "finished"
