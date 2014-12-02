#!/bin/bash
# written by Jeffrey Scherling Tue May 27 04:26:22 CEST 2014
# pull and install kde packages from alien 

# version
#VER="14.0"
VER="current"

CWD=$(pwd)
HOS=$(hostname)

# path to save 
TAR="/media/"$HOS"_system/packages/alien/kde"

# mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/alien-kde/$VER"
#RSYNCURL="rsync://alien.slackbook.org/alien-kde/$VER"
LFTPURL="http://alien.slackbook.org/ktown/$VER/" 
#LFTPURL="http://alien.slackbook.org/kdesc/$VER/" 

if [ ! -d $TAR ] ; then
	mkdir -p $TAR 
fi

cd $TAR

# get version
cd $VER
NUM="$(echo [4-9]* | cut -d " " -f 1)"
cd -

check_ver() {
# find name of current-version
echo -e "\n <version>: \t\t [\033[1;35m  $NUM  \033[0m]\n"
}

check_ver_dir() {
# control directory
if [ ! -d $VER/$NUM ] ; then
	echo ERROR WRONG DIRECTORY
	echo ERROR WRONG DIRECTORY
	exit 1
fi
}

pull() {
	rsync -avz --delete --progress --exclude "source" --exclude "x86" $RSYNCURL .
	echo -e "\n rsync <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

pull_lftp() {
	mkdir -p $VER ; cd $VER
	lftp -c "open $LFTPURL ; mirror -c -e x86_64"
	lftp -c "open $LFTPURL ; mirror -c -e README"
	cd $TAR
	echo -e "\n lftp <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

inst_kde() {
	# install kde
	upgradepkg --reinstall --install-new $VER/$NUM/x86_64/deps/*.t?z
	echo -e "\n depencies <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --reinstall --install-new $VER/$NUM/x86_64/kde/*.t?z
	echo -e "\n packages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# removepkg packagename
	# echo -e "\n removepkg <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	for lang in de ; do 
  		upgradepkg --reinstall --install-new $VER/$NUM/x86_64/kdei/kde-l10n-$lang-*.t?z
  		upgradepkg --reinstall --install-new $VER/$NUM/x86_64/kdei/calligra-l10n-$lang-*.t?z
	done
	echo -e "\n languages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n upgrade <kde-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m check .new config files!  \033[0m]\n"
	find /etc/ -name "*.new"
	echo -e "\n [\033[1;35m reboot in kde-$VER \033[0m]\n"
}

upgr_kde() {
	# upgrade kde
	upgradepkg --install-new $VER/$NUM/x86_64/deps/*.t?z
	echo -e "\n depencies <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --install-new $VER/$NUM/x86_64/kde/*.t?z
	echo -e "\n packages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# removepkg packagename
	# echo -e "\n removepkg <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	for lang in de ; do 
  		upgradepkg --install-new $VER/$NUM/x86_64/kdei/kde-l10n-$lang-*.t?z
  		upgradepkg --install-new $VER/$NUM/x86_64/kdei/calligra-l10n-$lang-*.t?z
	done
	echo -e "\n languages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n upgrade <kde-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m check .new config files!  \033[0m]\n"
	find /etc/ -name "*.new"
	echo -e "\n [\033[1;35m reboot in kde-$VER \033[0m]\n"
}

dry_kde() {
	# dry kde
	upgradepkg --dry-run --reinstall --install-new $VER/$NUM/x86_64/deps/*.t?z
	echo -e "\n depencies-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --dry-run --reinstall --install-new $VER/$NUM/x86_64/kde/*.t?z
	echo -e "\n packages-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# removepkg -warn packagename
	# echo -e "\n removepkg-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	for lang in de ; do 
  		upgradepkg --dry-run --reinstall --install-new $VER/$NUM/x86_64/kdei/kde-l10n-$lang-*.t?z
  		upgradepkg --dry-run --reinstall --install-new $VER/$NUM/x86_64/kdei/calligra-l10n-$lang-*.t?z
	done
	echo -e "\n languages-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n upgrade-dryrun <kde-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
}

timestamp() {
	cd $CWD
	# timestamp
	DATE="`date +%Y-%b-%d`"
	STMP="`basename $0 | cut -d - -f -3`"_"$DATE"
	rm -f "`basename $0`"*_*
	touch $STMP
}

# check_ver first to get directory
case "$1" in
	pull)
		pull
		check_ver
		;;
	pull_lftp)
		pull_lftp
		check_ver
		;;
	inst_kde)
		check_ver
		check_ver_dir
		inst_kde
		timestamp
		;;
	upgr_kde)
		check_ver
		check_ver_dir
		upgr_kde
		timestamp
		;;
	dry_kde)
		check_ver
		check_ver_dir
		dry_kde
		;;
	inst_all)
		pull
		check_ver
		check_ver_dir
		inst_kde
		timestamp
		;;
	sync)
		pull
		check_ver
		check_ver_dir
		upgr_kde
		timestamp
		;;
	check_ver)
		check_ver
		;;
	check_ver_dir)
		check_ver_dir
		;;
	*)
		echo "usage: `basename $0` { pull | pull_lftp | inst_kde | upgr_kde | dry_kde | check_ver | check_ver_dir | inst_all | sync }"
		exit 1
esac

