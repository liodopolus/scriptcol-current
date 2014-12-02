#!/bin/bash
# written by Jeffrey Scherling Fri Jul 30 00:22:42 CEST 2010
# get slackware64-version extra packages from alien

# get all
ALL="no"

# get selected packages
ONL="yes"

# version and location information
VER="13.1"
URL="http://connie.slackware.com/~alien/slackbuilds"
CWD=$(pwd)
LOC="$CWD/alien-extra-packages-$VER"


if [ ! -d $LOC ] ; then 
	mkdir $LOC
fi

# select packages
PKG="tigervnc"


if [ "$ONL" = "yes" ] ; then 

for i in $PKG ; do
	cd $LOC
	lftp -c "open $URL/$i/pkg64/$VER ; mirror -c -e -p --parallel=5"
	echo -e "\n Download $i: \t [\033[1;35m  OK  \033[0m]\n"
done

	echo -e "\n Syncing <alien-extra-packages-$VER>: \t [\033[1;35m  OK  \033[0m]\n"
fi


if [ "$ALL" = "yes" ] ; then 

cd $LOC

# get package list
rm -f FILELIST.TXT
wget http://connie.slackware.com/~alien/slackbuilds/FILELIST.TXT

# select packages by version and arch
PKG=$(grep -e .*pkg64.*$VER.*txt$ FILELIST.TXT | cut -d / -f 5- | sed -e s/-[0-9].*$//)
rm -f FILELIST.TXT

for i in $PKG ; do
	mkdir -p $LOC/$i
	cd $LOC/$i
	lftp -c "open $URL/$i/pkg64/$VER ; mirror -c -e -p --parallel=5"
	echo -e "\n Download $i: \t [\033[1;35m  OK  \033[0m]\n"
done

	echo -e "\n Syncing <alien-extra-packages-$VER>: \t [\033[1;35m  OK  \033[0m]\n"
fi


# -
