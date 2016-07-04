#!/bin/sh
# written by Jeffrey Scherling Sat Jan  2 16:32:10 CET 2016
# rsync script for slackwarearm-current

CWD=$(pwd)

DIS="slackwarearm"
#VER=${VER:-current}
VER=${VER:-14.1}

HOS=$(hostname)
TAR=/media/"$HOS"_system/slackware/$DIS-$VER

RSYNCURL="ftp.arm.slackware.com::slackwarearm"
#RSYNCURL="ftp.halifax.rwth-aachen.de::slackwarearm"
#RSYNCURL="rsync.slackware.pl::slackwarearm"

#rsync -Pavv --delete ftp.arm.slackware.com::slackwarearm/slackwarearm-current .
#ftp://ftp.arm.slackware.com/slackwarearm/slackwarearm-14.0/


if [ ! -d $TAR ]; then
	echo "Creating Target Directory $TAR !"
	mkdir -p $TAR
	echo ""
fi


cd $TAR


pull() {
	echo "pull $DIS-$VER to $TAR"
		rsync -avz --delete --progress --exclude "pasture" --exclude "source" --exclude "kde" --exclude "kdei" --include "xfce" $RSYNCURL/$DIS-$VER/ .
	echo -e "\n <pull $DIS-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dry() {
	echo "dry $DIS-$VER to $TAR" 
		rsync -n -avz --delete --exclude "pasture" --exclude "source" --exclude "kde" --exclude "kdei" --include "xfce" $RSYNCURL/$DIS-$VER/ .
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

