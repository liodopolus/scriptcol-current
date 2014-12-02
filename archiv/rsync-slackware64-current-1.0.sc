#!/bin/sh
# written by Jeffrey Scherling Mon Apr 30 17:43:23 CEST 2012
# rsync script for slackware64-current

CWD=`pwd`
DIS="slackware64"
VER=${VER:-current}
TAR=/media/gamma_system/source/slackware/$DIS-$VER

RSYNCURL="rsync://ftp.gwdg.de/pub/linux/slackware"
#RSYNCURL="rsync://taper.alienbase.nl/mirrors/slackware"
#RSYNCURL="rsync.slackware.pl::slackware"

if [ ! -d $TAR ]; then
	echo "Creating Target Directory $TAR !"
	mkdir -p $TAR
	echo ""
fi

cd $TAR

pull() {
	echo "Syncing version '$DISTRIB-$VERSION' to $TAR ..."
	rsync $1 -avz --delete --exclude "pasture/*" --exclude "source/*" $RSYNCURL/$DIS-$VER/ .
}

dry() {
	echo "Dry-Run version '$DISTRIB-$VERSION' to $TAR ..."
	rsync -n -avz --delete --exclude "pasture/*" --exclude "source/*" $RSYNCURL/$DIS-$VER/ .
}

case "$1" in
  pull)
     pull
    ;;
  dry)
    dry
    ;;
  *)
  echo ""
  echo "usage: `basename $0` {pull|dry}"
  echo ""
esac

# ---
