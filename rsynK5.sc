#!/bin/bash
# rsynK5.sc download script written by Jeffrey Scherling Fri Oct 16 01:44:11 CEST 2015
# for regulary updates have a look at https://www.kde.org/announcements/

# set K5 Version
K5V="15.08.15"

# set K5 Versions 13Okt15
K5AV="15.08.2"
K5FV="5.15"
K5PV="5.4.2"
K5CV="2.9.8"


# get global variables
CWD=$(pwd)
HOST=$(hostname)

# set target dir
TAR="/media/"$HOST"_sftp/rsync/K5/source/$K5V"

# check and create target dir
if [ ! -d $TAR ] ; then
	mkdir -p  $TAR
fi


# set rsyn url
RSYNCURL="rsync://ftp5.gwdg.de/pub/linux/kde/stable"

# set K5 directories

# K5 Applications
K5A="applications/$K5AV"

# K5 Frameworks
K5F="frameworks/$K5FV"

# K5 Plasma
K5P="plasma/$K5PV"

# K5 Calligra
K5C="calligra-$K5CV"

# set download list
LIST="$K5A $K5F $K5P $K5C"


# set functions

K5_pull_dry() {

# get packages from list dry run
for DIR in $LIST ; do
	mkdir -p $TAR/$DIR
	rsync -avz --dry-run --delete --progress --keep-dirlinks --copy-links $RSYNCURL/$DIR/ $TAR/$DIR
done

echo -e "\n K5-$K5V rsync: \t\t [\033[1;35m  ok  \033[0m]\n"

}


K5_pull() {

# get packages from list
for DIR in $LIST ; do
	mkdir -p $TAR/$DIR
	rsync -avz --delete --progress --keep-dirlinks --copy-links $RSYNCURL/$DIR/ $TAR/$DIR
done

echo -e "\n K5-$K5V rsync: \t\t [\033[1;35m  ok  \033[0m]\n"

}


# set cases
case $1 in
	-K5_pull_dry)
		K5_pull_dry
		;;
	-K5_pull)
		K5_pull
		;;
	*)
		echo ""
		echo "usage: `basename $0` [Option]"
		echo ""

		echo "Options"
	        echo "	-K5_pull_dry"
	        echo "	-K5_pull"
		echo ""

		exit 1
esac

