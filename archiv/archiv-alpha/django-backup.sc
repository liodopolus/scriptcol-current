#!/bin/sh
# written by Jeffrey Scherling Tue Aug 10 00:18:21 CEST 2010
# backup djangoprojects to /mnt/movies/Backups

CWD=$(pwd)
TAR="/mnt/movies/Backups/"
DATE="`date +%Y-%m-%d_%H-%M`"
FILE="Djangoprojects-$DATE-jsb.tar.gz"

tar -cvzf $TAR/$FILE $CWD

echo -e "\n Djangobackup: \t [\033[1;35m  OK  \033[0m]\n"
