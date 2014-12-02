#!/bin/sh
# written by Jeffrey Scherling Sun Jul 15 12:46:55 CEST 2012
# rsync  HOS to REM, SRC to TAR, gamma to alpha, DIR configs

CWD=$(pwd)

set -e # exit on most errors

# remote_machine
REM="alpha"
HOS=$(hostname)

# dir or file to sync, dir(/) is equal to cp dir/*
DIR="configs/"

# paths 
SRC=/media/"$HOS"_system/$DIR
TAR=/media/"$REM"_system/$DIR


if [ $REM = $HOS ] ; then
	echo ""
	echo "REMOTE = HOST"
	echo ""
	exit 1
fi	


if [ $SRC = $TAR ] ; then
	echo ""
	echo "SOURCE = TARGET"
	echo ""
	exit 1
fi


push() {
rsync -av --progress $SRC $REM:$TAR
}

pull() {
rsync -av --progress $REM:$TAR $SRC
}

push_del() {
rsync -av --progress --delete $SRC $REM:$TAR
}

pull_del() {
rsync -av --progress --delete $REM:$TAR $SRC
}


case "$1" in
	push)
		push
		;;
	pull)
		pull
		;;
	sync)
		push
		pull
		;;
	push_del)
		push_del
		;;
	pull_del)
		pull_del
		;;
	*)
		echo "usage: `basename $0` { push | pull | sync | push_del | pull_del }"
		echo "_del delete not present on dest"
		exit 1
esac

