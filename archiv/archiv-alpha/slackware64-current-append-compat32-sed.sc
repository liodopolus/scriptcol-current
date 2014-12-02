#!/bin/sh
# written by Jeffrey Scherling Sun Mar  7 15:05:00 CET 2010
# append -compat32 string in each line of the given 
# up to date file (/usr/sbin/massconvert32.sh)

MASSCONVERT="yes"
BEF=""
AFT="yes"
STRING="-compat32"

FILE=$1
MASSFILE="/usr/sbin/massconvert32.sh"
TMP="tmpfile"
INPUT="inputfile"
OUTPUT="Blacklist-append-compat32.new"


if [ "$MASSCONVERT" = "yes" ] ; then
       	grep -A 600 "# This is the script's internal list" $MASSFILE > $TMP
	grep -B 600 "# Create target directories" $TMP > $INPUT
	FILE=$INPUT
	rm $TMP
fi


if [ ! -f $FILE ] ; then 
	echo -e "\n [ $FILE ] not exist! \n" 
	exit 1 
fi


if [ "$BEF" = "yes" ] ; then
	cat $INPUT | sed s/^/$STRING/ > $OUTPUT
fi

if [ "$AFT" = "yes" ] ; then
	cat $INPUT | sed s/$/$STRING/ > $OUTPUT
fi


echo -e "\n Appending: \t\t [\033[1;35m  Finished  \033[0m]\n"

# -

