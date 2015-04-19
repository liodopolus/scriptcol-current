#!/bin/sh
# written by Jeffrey Scherling
# rsync script for kde 4.14.3 

CWD=$(pwd)

DIS="kde"
#VER=${VER:-current}
VER=${VER:-4.14.3}

HOS=$(hostname)
TAR=/media/"$HOS"_system/source/kde/$DIS-$VER

RSYNCURL="rsync://taper.alienbase.nl/mirrors/alien-kde/source/4.14.3/kde"
#RSYNCURL="rsync.slackware.pl::slackwarearm"
#
#RSYNCURL="ftp.arm.slackware.com/slackwarearm"
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
		rsync -avz --delete --progress $RSYNCURL/ .
	echo -e "\n <pull $DIS-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dry() {
	echo "dry $DIS-$VER to $TAR" 
		rsync -n -avz --delete --progress $RSYNCURL/ .
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

