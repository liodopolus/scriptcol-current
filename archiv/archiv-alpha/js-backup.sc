#!/bin/sh
# written by Jeffrey Scherling Fri Apr 16 21:51:00 CEST 2010
# Jeffs Backup Script (jsb) 

# /mnt/movies settings #
# Aikidokyritz -> .aikidokyritz//
# Backups -> .backups//
# Bilder -> .bilder//
# Filme -> .filme//
# Musik -> .musik//
# Privat -> .privat//
# System -> .system//
# Testing -> .testing//
# VMware-tmp/
# VMwareAppliances/
# VirtualBox -> .virtualbox//
# Windows-xp-backup -> .windows-xp-backup//
# Wineprogs -> .wineprogs//

# Targets
PRIVATE="no"
CONFIGS="no"
SYSTEM="no"
HOME="yes"


CWD=$(pwd)
DATE="`date +%Y-%m-%d_%H-%M`"
EXT="jsb.tar.gz"
STMP="Last-Backup-$DATE"

# Source #
SRC="/mnt/movies" 

# Target #
TAR="$SRC/Backups/Save" 


if [ "$PRIVATE" = "yes"  ] ; then

L1="Privat" # location 1
D1="Belege Filme Flirten Jeffrey Sonstiges" # directories
mkdir -p $TAR/$L1 # target directory

cd $SRC/$L1 # go to Private 

for i in $D1 ; do
	( tar -czf "$TAR/$L1/$DATE-$i-$EXT" $i )
	echo -e "\n Backup: [ $i ] \t\t [\033[1;35m  OK  \033[0m]\n"
done

rm Last-Backup-*
touch $STMP #create timestamp


fi


if [ "$CONFIGS" = "yes" ] ; then

R="/" # location 2
D2="boot etc" # directories 
L2="System/configs" # target directory

cd $R # go to Root "/"

for i in $D2 ; do
	( tar -czf "$SRC/$L2/$DATE-$i-$EXT" $i )
	echo -e "\n Backup: [ $i ] \t\t [\033[1;35m  OK  \033[0m]\n"
done

rm Last-Backup-*
touch $STMP #create timestamp


fi


if [ "$SYSTEM" = "yes" ] ; then

L3="System" # location 3 
D3="configs howtos packages scripts slackbuilds source" # directories
mkdir -p $TAR/$L3 # target directory

cd $SRC/$L3 # go to System

for i in $D3 ; do
	( tar -czf "$TAR/$L3/$DATE-$i-$EXT" $i )
	echo -e "\n Backup: [ $i ] \t\t [\033[1;35m  OK  \033[0m]\n"
done

rm Last-Backup-*
touch $STMP #create timestamp


fi


if [ "$HOME" = "yes" ] ; then

H="/home"
L4="home" # location 3 
D4="jeffrey" # directories
mkdir -p $TAR/$L4 # target directory

cd $H # go to Home

for i in $D4 ; do
	( tar -czf "$TAR/$L4/$DATE-$i-$EXT" $i )
	echo -e "\n Backup: [ $i ] \t\t [\033[1;35m  OK  \033[0m]\n"
done

rm Last-Backup-*
touch $STMP #create timestamp


fi


# Backup Finished #
echo -e "\n Backup Finished: \t\t [\033[1;35m  OK  \033[0m]\n"

# -
