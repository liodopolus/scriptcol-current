#!/bin/sh
#---js---#
# extract files from tar.gz archiv
# usage script + ARG + ARG e.g. <scrip archiv filename>
# e.g. ./tar-extract-files.sc backup-boot-2010-02-12--18-43.tar.gz  menu.lst

ARCHIV=$1 # 1. ARG to script
FILE=$2 # 2. ARG to script

tar -xf "$ARCHIV" --wildcards --no-anchored "$FILE" 

echo -e "\n $FILE of $ARCHIV extracted \n"

#---js---#

