#!/bin/sh
# written by Jeffrey Scherling Sat Sep 10 13:32:27 CEST 2011
# make alpha and beta mountpoints

DIRLIST="windows7 slackware64 home system backup sftp vm private movies" 
SYS1="alpha"
SYS2="beta"

for DIR in $DIRLIST ; do
	mkdir -p "$SYS1"_"$DIR"
	mkdir -p "$SYS2"_"$DIR"
done

# specials
mkdir -p {icyone,icytwo-lin,icytwo-win}


# -
