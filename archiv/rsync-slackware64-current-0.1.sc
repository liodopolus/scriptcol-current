#!/bin/sh
# written by Jeffrey Scherling Sat Jul 21 02:10:12 CEST 2012
# rsync script for slackware64-current

CWD=$(pwd)

DIS="slackware64"
VER=${VER:-current}

HOS=$(hostname)
TAR=/media/"$HOS"_system/packages/slackware/$DIS-$VER

RSYNCURL="rsync://mirror.netcologne.de/slackware"
#RSYNCURL="rsync://ftp5.gwdg.de/pub/linux/slackware"
#RSYNCURL="rsync://taper.alienbase.nl/mirrors/slackware"
#RSYNCURL="rsync.slackware.pl::slackware"


if [ ! -d $TAR ]; then
	echo "Creating Target Directory $TAR !"
	mkdir -p $TAR
	echo ""
fi


cd $TAR


pull() {
	echo "pull $DIS-$VER to $TAR"
		rsync -avz --delete --progress --exclude "pasture" --exclude "source" $RSYNCURL/$DIS-$VER/ .
	echo -e "\n <pull $DIS-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dry() {
	echo "dry $DIS-$VER to $TAR" 
		rsync -n -avz --delete --exclude "pasture" --exclude "source" $RSYNCURL/$DIS-$VER/ .
	echo -e "\n <dry $DIS-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}


case "$1" in
  pull)
     pull
    ;;
  dry)
    dry
    ;;
  *)
  echo -e "\n usage: `basename $0` { pull | dry }\n"
esac

