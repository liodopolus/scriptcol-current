#!/bin/bash
# written by Jeffrey Scherling Sun Jul 15 16:54:08 CEST 2012
# pull and install multilib and compat32 packages from alien 

# version
VER="current"

CWD=$(pwd)
HOS=$(hostname)

# path to save
TAR="/media/"$HOS"_system/packages/alien/multilib"

# mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/multilib/$VER"
#RSYNCURL="rsync://slackware.org.uk/people/alien/multilib/$VER"
LFTPURL="http://taper.alienbase.nl/mirrors/people/alien/multilib/"
#LFTPURL="http://connie.slackware.com/~alien/multilib/"

# check dir
if [ ! -d $TAR ] ; then
	mkdir -p $TAR
fi

cd $TAR


pull() {
	# get README
	wget -c "$LFTPURL/README"
	rsync -avz --delete --progress --exclude "debug" --include "slackware64-compat32" $RSYNCURL .
	echo -e "\n rsync <multilib-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

pull_lftp() {
	# get README
	wget -c "$LFTPURL/README"
	lftp -c "open $LFTPURL ; mirror -c -e $VER"
	echo -e "\n lftp <multilib-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

inst_mul() {
	# install multilib
	upgradepkg --reinstall --install-new $VER/*.t?z
	echo -e "\n install <multilib-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

upgr_mul() {
	# upgrade multilib
	upgradepkg --install-new $VER/*.t?z
	echo -e "\n upgrade <multilib-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dry_mul() {
	# dry multilib
	upgradepkg --dry-run --reinstall --install-new $VER/*.t?z
	echo -e "\n dryrun <multilib-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"

}

inst_com() {
	# install compat
	DIR=`echo $VER/slackware64-compat32/*compat32`

	for C32 in $DIR ; do
		upgradepkg --reinstall --install-new $C32/*.t?z
	done
	echo -e "\n install <compat32-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}


upgr_com() {
	# upgrade compat
	DIR=`echo $VER/slackware64-compat32/*compat32`
	for C32 in $DIR ; do
		upgradepkg --install-new $C32/*.t?z
	done
	echo -e "\n upgrade <compat32-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dry_com() {
	# dry compat
	DIR=`echo $VER/slackware64-compat32/*compat32`
	for C32 in $DIR ; do
		upgradepkg --dry-run --reinstall --install-new $C32/*.t?z
	done
	echo -e "\n dryrun <compat32-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}


timestamp() {
	cd $CWD
	# timestamp
	DATE="`date +%Y-%b-%d`"
	STMP="`basename $0 | cut -d - -f -3`"_"$DATE"
	rm -f "`basename $0`"*_*
	touch $STMP
}

case "$1" in
	pull)
		pull
		;;
	pull_lftp)
		pull_lftp
		;;
	inst_mul)
		inst_mul
		timestamp
		;;
	upgr_mul)
		upgr_mul
		timestamp
		;;
	dry_mul)
		dry_mul
		;;
	inst_com)
		inst_com
		timestamp
		;;
	upgr_com)
		upgr_com
		timestamp
		;;
	dry_com)
		dry_com
		;;
	inst_all)
		pull
		inst_mul
		inst_com
		timestamp
		;;
	sync)
		pull
		upgr_mul
		upgr_com
		timestamp
		;;
	*)
		echo "usage: `basename $0` { pull | pull_lftp | inst_mul | upgr_mul | dry_mul | inst_com | upgr_com | dry_com | inst_all | sync }"
		exit 1
esac

