#!/bin/bash
# written by Jeffrey Scherling Sat Sep 27 13:32:01 CEST 2014
# pull and install kde packages from alien 

# version
#VER="4.14.1"
VER="latest"
#VER="testing"

# locations
CWD=$(pwd)
HOS=$(hostname)

# path to save 
TAR="/media/"$HOS"_system/packages/alien/kde/current"

# mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/alien-kde/current/$VER"
#RSYNCURL="rsync://alien.slackbook.org/alien-kde/current/$VER"
LFTPURL="http://alien.slackbook.org/ktown/current/$VER" 
#LFTPURL="http://alien.slackbook.org/kdesc/$VER" 

if [ ! -d $TAR ] ; then
	mkdir -p $TAR 
fi

cd $TAR

# get version
#VER="$(echo [4-9]* | cut -d " " -f 1)"

show_ver() {
# show version to download 
echo -e "\n <version>: \t\t [\033[1;35m  $VER  \033[0m]\n"
}

check_down_dir() {
# check if download directory exist and go into it
if [ ! -d $TAR ] ; then
	echo DIRECTORY DOES NOT EXIST
	echo DIRECTORY DOES NOT EXIST
	exit 1

elif [ -d $TAR ] ; then
	cd $TAR
	echo READY TO PROCESS
	echo READY TO PROCESS
else
	echo SOMETHING WRONG
	echo SOMETHING WRONG
	exit 1
fi
}

pull() {
	#rsync -avz --delete --progress --exclude "source" --exclude "x86" $RSYNCURL .
	#transform links in dir: --copy-links \
	rsync \
	-avz \
	--delete \
	--delete-excluded \
	--progress \
	--exclude "source" \
	--exclude "x86" \
	--include "kdei/kde-l10n-de*" \
	--include "kdei/calligra-l10n-de*" \
	--exclude "kdei/*.txz" \
	--exclude "kdei/*.txt" \
	--exclude "kdei/*.asc" \
	--exclude "kdei/*.md5" \
	--exclude "kdei/*.lst" \
	--exclude "kdei/*.meta" \
	--links \
	--copy-links \
	$RSYNCURL .

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
	upgradepkg --reinstall --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n depencies <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --reinstall --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n packages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# removepkg packagename
	# echo -e "\n removepkg <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	for lang in de ; do 
  		upgradepkg --reinstall --install-new $VER/x86_64/kdei/kde-l10n-$lang-*.t?z
  		upgradepkg --reinstall --install-new $VER/x86_64/kdei/calligra-l10n-$lang-*.t?z
	done
	echo -e "\n languages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n upgrade <kde-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m check .new config files!  \033[0m]\n"
	find /etc/ -name "*.new"
	echo -e "\n [\033[1;35m reboot in kde-$VER \033[0m]\n"
}

upgr_kde() {
	# upgrade kde
	upgradepkg --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n depencies <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n packages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# removepkg packagename
	# echo -e "\n removepkg <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	for lang in de ; do 
  		upgradepkg --install-new $VER/x86_64/kdei/kde-l10n-$lang-*.t?z
  		upgradepkg --install-new $VER/x86_64/kdei/calligra-l10n-$lang-*.t?z
	done
	echo -e "\n languages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n upgrade <kde-$VER<: \t\t [\033[1;35m  ok  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m check .new config files!  \033[0m]\n"
	find /etc/ -name "*.new"
	echo -e "\n [\033[1;35m reboot in kde-$VER \033[0m]\n"
}

dry_kde() {
	# dry kde
	upgradepkg --dry-run --reinstall --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n depencies-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --dry-run --reinstall --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n packages-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# removepkg -warn packagename
	# echo -e "\n removepkg-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	for lang in de ; do 
  		upgradepkg --dry-run --reinstall --install-new $VER/x86_64/kdei/kde-l10n-$lang-*.t?z
  		upgradepkg --dry-run --reinstall --install-new $VER/x86_64/kdei/calligra-l10n-$lang-*.t?z
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

# show_ver first to get directory
case "$1" in
	pull)
		check_down_dir
		pull
		show_ver
		;;
	pull_lftp)
		check_down_dir
		pull_lftp
		show_ver
		;;
	inst_kde)
		check_down_dir
		inst_kde
		timestamp
		show_ver
		;;
	upgr_kde)
		check_down_dir
		upgr_kde
		timestamp
		show_ver
		;;
	dry_kde)
		check_down_dir
		dry_kde
		show_ver
		;;
	inst_all)
		pull
		inst_kde
		;;
	sync)
		pull
		upgr_kde
		;;
	show_ver)
		show_ver
		;;
	check_down_dir)
		check_down_dir
		;;
	*)
		echo "usage: `basename $0` { pull | pull_lftp | inst_kde | upgr_kde | dry_kde | show_ver | check_down_dir | inst_all | sync }"
		exit 1
esac

