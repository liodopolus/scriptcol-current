#!/bin/bash
# written by Jeffrey Scherling Sat Oct  8 20:00:13 CEST 2011
# get and install multilib packages from alien 

VER="current"

# install yes|no|dry|upg
INS=$1

# install compat
COM=$2

# path to save
TAR="/media/gamma_system/packages/alien/multilib"
CWD=$(pwd)

if [ ! -d $TAR ] ; then
	mkdir -p $TAR ; cd $TAR
elif [ -d $TAR ] ; then
	cd $TAR
else
	exit 1
fi


# get packages yes|no
RSYNC="yes"
LFTP="no"

# rsync mirror
RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/multilib/$VER"
#RSYNCURL="rsync://slackware.org.uk/people/alien/multilib/$VER"

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
	lftp -c "open $LFTPURL ; mirror -c -e $VER"
	echo -e "\n LFTP <Multilib-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$LFTP" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n LFTP <Multilib-$VER>: \t\t [\033[1;35m  ER  \033[0m]\n"
	exit 1
fi


# install the packages

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


# install the compat32 packages

DIR=`echo $VER/slackware64-compat32/*compat32`

if [ "$COM" = "yes" ] ; then 
	for C32 in $DIR ; do
		upgradepkg --reinstall --install-new $C32/*.t?z
	done
	echo -e "\n Install <Compat32-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# timestamp
	DATE="`date +%Y-%m-%d_%H-%M`"
	STMP="multilib-compat32-upgrade-$DATE"
	rm -f $CWD/multilib-compat32-upgrade-*
	touch $CWD/$STMP
	echo -e "\n Upgrade <Compat32-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$COM" = "upg" ] ; then 
	for C32 in $DIR ; do
		upgradepkg --install-new $C32/*.t?z
	done
	echo -e "\n Install <Compat32-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"
	# timestamp
	DATE="`date +%Y-%m-%d_%H-%M`"
	STMP="multilib-compat32-upgrade-$DATE"
	rm -f $CWD/multilib-compat32-upgrade-*
	touch $CWD/$STMP
	echo -e "\n Upgrade <Compat32-$VER<: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$COM" = "dry" ] ; then 
	for C32 in $DIR ; do
		upgradepkg --dry-run --reinstall --install-new $C32/*.t?z
	done
	echo -e "\n Install-DryRun <Compat32-$VER>: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$COM" = "no" ] ; then 
	echo -e "\n"
else
	echo -e "\n\t\t [\033[1;35m  ER \033[0m]\n"
	exit 1
fi

# -
