#!/bin/bash
# written by Jeffrey Scherling Mon Oct  3 21:29:14 CEST 2011
# get slackware64-version extra packages from alien with rsync


# get only selected packages
ONL="yes"

# get only selected restricted_packages
ONLRES="no"

# get all packages
ALL="no"

# get all restricted_packages
RES="no"

# selected packages
PKG="$1"
#PKG="corefonts mplayer-codecs"

# version and location information
#VER="13.1"
VER="13.37"


CWD=$(pwd)
LOC="$CWD/alien-expkgs-$VER"
RES_DIR="$CWD/alien-expkgs-restricted-$VER"


# only some packages
if [ "$ONL" = "yes" ] ; then 

if [ ! -d $LOC ] ; then 
	mkdir -p $LOC ; cd $LOC
elif [ -d $LOC ] ; then 
	cd $LOC
else
	exit 1
fi

for i in $PKG ; do
	RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/slackbuilds/$i/pkg64/$VER"
	mkdir -p $i ; cd $i
	rsync -avz --delete --progress $RSYNCURL .
	echo -e "\n Download $i: \t [\033[1;35m  OK  \033[0m]\n"
	cd $LOC
done
	echo -e "\n Syncing <alien-expkgs-$VER>: \t [\033[1;35m  OK  \033[0m]\n"
fi


# only some restricted packages
if [ "$ONLRES" = "yes" ] ; then 

if [ ! -d $RES_DIR ] ; then 
	mkdir -p $RES_DIR ; cd $RES_DIR
elif [ -d $RES_DIR ] ; then 
	cd $RES_DIR
else
	exit 1
fi

for i in $PKG ; do
	RES_RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/restricted_slackbuilds/$i/pkg64/$VER"
	mkdir -p $i ; cd $i
	rsync -avz --delete --progress $RES_RSYNCURL .
	echo -e "\n Download $i: \t [\033[1;35m  OK  \033[0m]\n"
	cd $RES_DIR
done
	echo -e "\n Syncing Restricted <alien-expkgs-$VER>: \t [\033[1;35m  OK  \033[0m]\n"
fi


# all packages
if [ "$ALL" = "yes" ] ; then 

if [ ! -d $LOC ] ; then 
	mkdir -p $LOC ; cd $LOC
elif [ -d $LOC ] ; then 
	cd $LOC
else
	exit 1
fi

# get package list
rm -f FILELIST.TXT
echo -n "\n Getting Filelist."
wget -c http://connie.slackware.com/~alien/slackbuilds/FILELIST.TXT

# select packages by version and arch
#PKG=$(grep -e .*pkg64.*$VER.*txt$ FILELIST.TXT | cut -d / -f 5- | sed -e s/-[0-9].*$//)
PKG=$(grep -e .*pkg64.*13.37$ FILELIST.TXT | cut -d . -f 2- | cut -d / -f 2)
rm -f FILELIST.TXT

for i in $PKG ; do
	RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/slackbuilds/$i/pkg64/$VER"
	mkdir -p $i ; cd $i
	rsync -avz --delete --progress $RSYNCURL .
	echo -e "\n Download $i: \t [\033[1;35m  OK  \033[0m]\n"
	cd $LOC
done
	echo -e "\n Syncing <alien-expkgs-$VER>: \t [\033[1;35m  OK  \033[0m]\n"
fi


# only restricted
if [ "$RES" = "yes" ] ; then 

if [ ! -d $RES_DIR ] ; then 
	mkdir -p $RES_DIR ; cd $RES_DIR
elif [ -d $RES_DIR ] ; then 
	cd $RES_DIR
else
	exit 1
fi

# get package list
rm -f FILELIST.TXT
echo -n "\n Getting Filelist."
wget -c http://taper.alienbase.nl/mirrors/people/alien/restricted_slackbuilds/FILELIST.TXT

# select packages by version and arch
#PKG=$(grep -e .*pkg64.*$VER.*txt$ FILELIST.TXT | cut -d / -f 5- | sed -e s/-[0-9].*$//)
PKG=$(grep -e .*pkg64.*13.37$ FILELIST.TXT | cut -d . -f 2- | cut -d / -f 2)
rm -f FILELIST.TXT

for i in $PKG ; do
	RES_RSYNCURL="rsync://taper.alienbase.nl/mirrors/people/alien/restricted_slackbuilds/$i/pkg64/$VER"
	mkdir -p $i ; cd $i
	rsync -avz --delete --progress $RES_RSYNCURL .
	echo -e "\n Download $i: \t [\033[1;35m  OK  \033[0m]\n"
	cd $RES_DIR
done
	echo -e "\n Syncing Restricted <alien-expkgs-$VER>: \t [\033[1;35m  OK  \033[0m]\n"
fi

# -
