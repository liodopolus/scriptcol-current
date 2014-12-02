#!/bin/sh
# written by Jeffrey Scherling Sat Jul 21 01:39:55 CEST 2012
# rsync  HOS to REM, SRC to TAR, LIS="dir1 dir2"

CWD=$(pwd)

set -e # exit on most errors

# remote_machine
REM="alpha"
HOS=$(hostname)

# dir or file to sync, always with /
LIS="aikido/ logins/ musik/ beta_private/"

# paths 
SRC=/media/"$HOS"_private
TAR=/media/"$REM"_private


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
	for DIR in $LIS ; do
		rsync -av --progress $SRC/$DIR $REM:$TAR/$DIR
		echo -e "<push $DIR>: \t\t [\033[1;35m  ok  \033[0m]\n"	
	done
}

pull() {
	for DIR in $LIS ; do
		rsync -av --progress $REM:$TAR/$DIR $SRC/$DIR
		echo -e "<pull $DIR>: \t\t [\033[1;35m  ok  \033[0m]\n"	
	done
}

push_del() {
	for DIR in $LIS ; do
		rsync -av --progress --delete $SRC/$DIR $REM:$TAR/$DIR
		echo -e "<push_del $DIR>: \t\t [\033[1;35m  ok  \033[0m]\n"	
	done
}

pull_del() {
	for DIR in $LIS ; do
		rsync -av --progress --delete $REM:$TAR/$DIR $SRC/$DIR
		echo -e "<pull_del $DIR>: \t\t [\033[1;35m  ok  \033[0m]\n"	
	done
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
		echo -e "\n usage: `basename $0` { push | pull | sync | push_del | pull_del }\n"
		exit 1
esac

