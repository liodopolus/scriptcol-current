#!/bin/sh
# written by Jeffrey Scherling Tue May 25 13:08:15 CEST 2010
# syncing slackware64-13.1-install-dvd.iso via rsync from http://rsync.slackware.pl/slackware/slackware64-13.1-iso/

FILE="slackware64-13.1-install-dvd.iso"
CWD=`pwd`
VERSION=${VERSION:-13.1}
TOPDIR=$CWD
URL="rsync.slackware.pl::slackware"

echo ""
echo "Syncing Slackware-$VERSION"
echo "--------------------------"


cd ${TOPDIR}/slamd64-$VERSION
rsync -avz --delete ${URL}/slackware64-$VERSION-iso/$FILE .

echo ""
echo "Syncing Slackware-$VERSION finished"
echo "-----------------------------------"

# -
