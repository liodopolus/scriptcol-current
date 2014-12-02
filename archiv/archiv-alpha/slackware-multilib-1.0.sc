#!/bin/bash
# written by Jeffrey Scherling Sat Oct  8 20:00:13 CEST 2011
# get and install multilib packages from alien 

VER="current"

# install yes|no|dry|upg
#INS="yes"
INS=$1

# path to save and to install
TAR="/media/alpha_system/packages/alien/multilib"
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
RSYNC="no"
LFTP="yes"

# rsync mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/multilib/$VER"

# lftp mirror
#LFTPURL="http://taper.alienbase.nl/mirrors/people/alien/multilib/"
LFTPURL="http://connie.slackware.com/~alien/multilib/"

# getting README
wget -c "$LFTPURL/README"

if [ "$RSYNC" = "yes" ] ; then 
	rsync -avz --delete --progress --exclude "debug" --include "slackware64-compat32" $RSYNCURL .
	echo -e "\n RSYNC <Multilib-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$RSYNC" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n RSYNC <Multilib-$VER>: \t\t [\033[1;35m  ER  \033[0m]\n"
	exit 1
fi

if [ "$LFTP" = "yes" ] ; then 
	lftp -c "open $LFTPURL ; mirror $VER"
	echo -e "\n LFTP <Multilib-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$LFTP" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n LFTP <Multilib-$VER>: \t\t [\033[1;35m  ER  \033[0m]\n"
	exit 1
fi

## second install the packages ##

if [ "$INS" = "yes" ] ; then 
	upgradepkg --reinstall --install-new $VER/*.t?z
	echo -e "\n Install <Multilib-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# timestamp
	DATE="`date +%Y-%m-%d_%H-%M`"
	STMP="multilib-upgrade-$DATE"
	rm -f $CWD/multilib-upgrade-*
	touch $CWD/$STMP
	echo -e "\n Upgrade <Multilib-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$INS" = "upg" ] ; then 
	upgradepkg --install-new $VER/*.t?z
	echo -e "\n Install <Multilib-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# timestamp
	DATE="`date +%Y-%m-%d_%H-%M`"
	STMP="multilib-upgrade-$DATE"
	rm -f $CWD/multilib-upgrade-*
	touch $CWD/$STMP
	echo -e "\n Upgrade <Multilib-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$INS" = "dry" ] ; then 
	upgradepkg --dry-run --reinstall --install-new $VER/*.t?z
	echo -e "\n Install-DryRun <Multilib-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$INS" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n\t\t [\033[1;35m  ER \033[0m]\n"
	exit 1
fi

# -
