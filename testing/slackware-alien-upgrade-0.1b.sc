#!/bin/sh
# written by Jeffrey Scherling Sun Jul 15 21:02:47 CEST 2012
# pull and install openjdk, rhino, etc. packages from alien

# version 
VER="13.37"

# arch
TYP="pkg64"

# package list
LIS_SLB="icedtea-web openjdk rhino"
LIS_RES="vlc ffmpeg MPlayer"

CWD=$(pwd)
HOS=$(hostname)

# path to save
TAR="/media/"$HOS"_system/packages/alien"
SLB="$TAR/slackbuilds"
RES="$TAR/restricted_slackbuilds"

# mirror
RSYNCURL_SLB="rsync://taper.alienbase.nl/mirrors/people/alien/slackbuilds"
RSYNCURL_RES="rsync://taper.alienbase.nl/mirrors/people/alien/restricted_slackbuilds"


# check dir
if [ ! -d $SLB ] ; then 
	mkdir -p  $SLB
fi

if [ ! -d $RES ] ; then 
	mkdir -p  $RES
fi

pull_onl() {
	# pull slackbuild
	for i in $2 ; do
		cd $SLB
		mkdir -p $i ; cd $i
		$(rsync -avz --delete --progress "$RSYNCURL_SLB/$i/$TYP/$VER" .)
		echo -e "\n <download $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

pull_res_onl() {
	# pull restricted_slackbuild
	for i in $2 ; do
		cd $RES
		mkdir -p $i ; cd $i
		rsync -avz --delete --progress $RSYNCURL_RES/$i/$TYP/$VER .
		echo -e "\n <download $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

pull_lis() {
	# pull slackbuilds
	for i in $LIS_SLB ; do
		cd $SLB
		mkdir -p $i ; cd $i
		rsync -avz --delete --progress --exclude "prevent" $RSYNCURL_SLB/$i/$TYP/$VER .
		echo -e "\n <download $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

pull_res_lis() {
	# pull restricted_slackbuilds
	for i in $LIS_RES ; do
		cd $RES
		mkdir -p $i ; cd $i
		rsync -avz --delete --progress $RSYNCURL_RES/$i/$TYP/$VER .
		echo -e "\n <download $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

pull_all() {
	# pull all slackbuilds
	cd $SLB
	# get package list
	rm -f FILELIST.TXT
	rsync -avz --delete --progress $RSYNCURL_SLB/FILELIST.TXT .
	# select packages by version and arch
	LIS_SLB_ALL=$(grep -e .*pkg64.*13.37$ FILELIST.TXT | cut -d . -f 2- | cut -d / -f 2)
	rm -f FILELIST.TXT
	for i in $LIS_SLB_ALL ; do
		cd $SLB
		mkdir -p $i ; cd $i
		rsync -avz --delete --progress $RSYNCURL_SLB/$i/$TYP/$VER .
		echo -e "\n <download $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

pull_res_all() {
	# pull all restricted_slackbuilds
	cd $RES
	# get package list
	rm -f FILELIST.TXT
	rsync -avz --delete --progress $RSYNCURL_RES/FILELIST.TXT .
	# select packages by version and arch
	LIS_RES_ALL=$(grep -e .*pkg64.*13.37$ FILELIST.TXT | cut -d . -f 2- | cut -d / -f 2)
	rm -f FILELIST.TXT
	for i in $LIS_RES_ALL ; do
		cd $RES
		mkdir -p $i ; cd $i
		rsync -avz --delete --progress $RSYNCURL_RES/$i/$TYP/$VER .
		echo -e "\n <download $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

inst_onl() {
	# install slackbuild
	for i in $2 ; do
		cd $SLB/$i/$VER
		upgradepkg --install-new "*.t?z"
		echo -e "\n <install $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

inst_res_onl() {
	# install restricted_slackbuild
	for i in $2 ; do
		cd $RES/$i/$VER
		upgradepkg --install-new "*.t?z"
		echo -e "\n <install_restricted $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}


inst_lis() {
	# install slackbuilds
	for i in $LIS_SLB ; do
		cd $SLB/$i/$VER
		# prevent to install
		$(mkdir -p prevent ; mv -f -u openjdk* prevent)
		upgradepkg --install-new "*.t?z"
		echo -e "\n <install $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
}

inst_res_lis() {
	# install restricted_slackbuilds
	for i in $LIS_RES ; do
		cd $RES/$i/$VER
		upgradepkg --install-new "*.t?z"
		echo -e "\n <install_restricted $i>: \t [\033[1;35m  ok  \033[0m]\n"
	done
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
	pull_onl)
		pull_onl
		;;
	pull_res_onl)
		pull_res_onl
		;;
	pull_lis)
		pull_lis
		;;
	pull_res_lis)
		pull_res_lis
		;;
	pull_all)
		pull_all
		;;
	pull_res_all)
		pull_res_all
		;;
	inst_onl)
		inst_onl
		timestamp
		;;
	inst_res_onl)
		inst_res_onl
		timestamp
		;;
	inst_lis)
		inst_lis
		timestamp
		;;
	inst_res_lis)
		inst_res_lis
		timestamp
		;;
	sync_onl)
		pull_onl
		inst_onl
		timestamp
		;;
	sync_res_onl)
		pull_res_onl
		inst_res_onl
		timestamp
		;;
	sync_lis)
		pull_lis
		inst_lis
		timestamp
		;;
	sync_res_lis)
		pull_res_lis
		inst_res_lis
		timestamp
		;;
	*)
		echo "usage: `basename $0` { pull_lis | pull_res_lis | pull_all | pull_res_all | inst_lis | inst_res_lis }"
		echo "usage: `basename $0` { pull_onl | pull_res_onl | inst_onl | inst_res_onl } { package | packages }"
		echo "usage: `basename $0` { sync_onl | sync_res_onl | sync_lis | sync_res_lis } { package | packages }"
		exit 1
esac

