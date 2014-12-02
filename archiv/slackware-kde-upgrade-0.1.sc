#!/bin/bash
# written by Jeffrey Scherling Sun Jul 15 16:54:12 CEST 2012
# pull and install kde packages from alien 

# version
VER="4.8.4"

CWD=$(pwd)
HOS=$(hostname)

# path to save 
TAR="/media/"$HOS"_system/packages/alien/kde"

if [ ! -d $TAR ] ; then
	mkdir -p $TAR 
fi

cd $TAR

# mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/alien-kde/$VER"
#RSYNCURL="rsync://alien.slackbook.org/alien-kde/$VER"
LFTPURL="http://alien.slackbook.org/ktown/$VER/" 
#LFTPURL="http://alien.slackbook.org/kdesc/$VER/" 


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
	upgradepkg --reinstall --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n depencies <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	upgradepkg --reinstall --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n packages <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
	# -----------------------------------
	# remove unused packages
	# removepkg xxxxxxxxxxxx
	# -----------------------------------
	echo -e "\n removepkg <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
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
	# remove unused packages
	# removepkg xxxxxxxxxxxx
	# -----------------------------------
	echo -e "\n removepkg <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
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
	# remove dry-run unused packages
	#removepkg -warn kdeedu
	# -----------------------------------
	echo -e "\n removepkg-dryrun <kde-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
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

case "$1" in
	pull)
		pull
		;;
	pull_lftp)
		pull_lftp
		;;
	inst_kde)
		inst_kde
		timestamp
		;;
	upgr_kde)
		upgr_kde
		timestamp
		;;
	dry_kde)
		dry_kde
		;;
	inst_all)
		pull
		inst_kde
		timestamp
		;;
	sync)
		pull
		upgr_kde
		timestamp
		;;
	*)
		echo "usage: `basename $0` { pull | pull_lftp | inst_kde | upgr_kde | dry_kde | inst_all | sync }"
		exit 1
esac

