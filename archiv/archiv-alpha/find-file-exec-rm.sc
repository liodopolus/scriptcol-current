#!/bin/sh
# written by Jeffrey Scherling Fri Apr 16 22:45:23 CEST 2010
# find files and remove them
# usage script + startpoint where begin to search

FILE='Last-Backup-*'
SRC=$1

find "$SRC" -iname "$FILE" -exec rm {} \;

echo -e "\n Removed Searched: [ $FILE ] \n"

# - 
