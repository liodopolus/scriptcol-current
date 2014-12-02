#!/bin/bash
# written by Jeffrey Scherling Sat Oct  8 19:34:06 CEST 2011
# get and install kde packages from alien 

VER="4.7.4"

# install yes|no|dry|upg
#INS="yes"
INS=$1

# path to save and to install
TAR="/media/alpha_system/packages/alien/kde"
CWD=$(pwd)

if [ ! -d $TAR ] ; then
	mkdir -p $TAR ; cd $TAR
elif [ -d $TAR ] ; then
	cd $TAR
else
	exit 1
fi

## first get the packages ##

# get packages yes|no
RSYNC="yes"
LFTP="no"

# rsync mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/alien-kde/$VER"
#RSYNCURL="rsync://alien.slackbook.org/alien-kde/$VER"

# lftp mirror
LFTPURL="http://alien.slackbook.org/ktown/$VER/" 
#LFTPURL="http://alien.slackbook.org/kdesc/$VER/" 

if [ "$RSYNC" = "yes" ] ; then 
	rsync -avz --delete --progress --exclude "source" --exclude "x86" $RSYNCURL .
	echo -e "\n RSYNC <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$RSYNC" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n RSYNC <KDE-$VER>: \t\t [\033[1;35m  ER  \033[0m]\n"
	exit 1
fi

if [ "$LFTP" = "yes" ] ; then 
	mkdir -p $VER ; cd $VER
	lftp -c "open $LFTPURL ; mirror x86_64"
	lftp -c "open $LFTPURL ; mirror README"
	cd $TAR
	echo -e "\n RSYNC <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$LFTP" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n LFTP <KDE-$VER>: \t\t [\033[1;35m  ER  \033[0m]\n"
	exit 1
fi

## second install the packages ##

# for removepkg see #remove unused packages

if [ "$INS" = "yes" ] ; then 
	upgradepkg --reinstall --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n Depencies <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	upgradepkg --reinstall --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n Packages <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# -----------------------------------
	# remove unused packages
	#removepkg kdeedu
	# -----------------------------------
	echo -e "\n Removepkg <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	for lang in de en_GB hu it pl ru ; do 
  		upgradepkg --reinstall --install-new $VER/x86_64/kdei/kde-l10n-$lang-*.t?z
	done
	echo -e "\n Languages <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n Upgrade <KDE-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m Check .new config Files!  \033[0m]\n"
	find /etc/ -name "*.new"
	# timestamp
	DATE="`date +%Y-%m-%d_%H-%M`"
	STMP="kde-upgrade-$DATE"
	rm -f $CWD/kde-upgrade-*
	touch $CWD/$STMP
	echo -e "\n [\033[1;35m Reboot in KDE-$VER \033[0m]\n"

elif [ "$INS" = "upg" ] ; then 
	upgradepkg --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n Depencies <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	upgradepkg --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n Packages <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# -----------------------------------
	# remove unused packages
	#removepkg kdeedu
	# -----------------------------------
	echo -e "\n Removepkg <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	for lang in de en_GB hu it pl ru ; do 
  		upgradepkg --install-new $VER/x86_64/kdei/kde-l10n-$lang-*.t?z
	done
	echo -e "\n Languages <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n Upgrade <KDE-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m Check .new config Files!  \033[0m]\n"
	find /etc/ -name "*.new"
	# timestamp
	DATE="`date +%Y-%m-%d_%H-%M`"
	STMP="kde-upgrade-$DATE"
	rm -f $CWD/kde-upgrade-*
	touch $CWD/$STMP
	echo -e "\n [\033[1;35m Reboot in KDE-$VER \033[0m]\n"

elif [ "$INS" = "dry" ] ; then 
	upgradepkg --dry-run --reinstall --install-new $VER/x86_64/deps/*.t?z
	echo -e "\n Depencies-DryRun <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	upgradepkg --dry-run --reinstall --install-new $VER/x86_64/kde/*.t?z
	echo -e "\n Packages-DryRun <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# -----------------------------------
	# remove dry-run unused packages
	#removepkg -warn kdeedu
	# -----------------------------------
	echo -e "\n Removepkg-DryRun <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	for lang in de en_GB hu it pl ru ; do 
  		upgradepkg --dry-run --reinstall --install-new $VER/x86_64/kdei/kde-l10n-$lang-*.t?z
	done
	echo -e "\n Languages-DryRun <KDE-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n Upgrade-DryRun <KDE-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$INS" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n\t\t [\033[1;35m  ER \033[0m]\n"
	exit 1
fi

# -
