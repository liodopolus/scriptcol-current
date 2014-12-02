#!/bin/sh
# written by Jeffrey Scherling Sun Jul  8 14:00:51 CEST 2012
# download sbopkg queuefiles from ponce and update queues in /var/lib/sbopkg/queues

CWD=$(pwd)
TMP=${TMP:-/tmp/sbopkg-queues}

PKG="sbopkg-slackware-queues-master.tgz"
SRC="sbopkg-slackware-queues-sbopkg-slackware-queues"
TAR=${TAR:-/var/lib/sbopkg/queues}

if [ ! -d $TMP ] ; then
	mkdir -p $TMP
fi

# do it in tmp
cd $TMP
wget -c --progress="bar" $OPT -- http://gitorious.org/sbopkg-slackware-queues/sbopkg-slackware-queues/archive-tarball/master
sleep 5s
mv master $PKG
cp $PKG $CWD # for backup only
tar xvf $PKG

if [ ! -d $TAR ] ; then
	mkdir -p $TAR
fi

cp -a $SRC/* $TAR # cp queue-files to lib

rm -r $TMP # cleanup

cd $CWD

# timestamp
DATE="`date +%Y-%m-%d_%H-%M`"
STMP="sbopkg-queues-upgrade-$DATE"
rm -f sbopkg-queues-upgrade-*
touch $STMP

