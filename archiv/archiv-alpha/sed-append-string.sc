#!/bin/sh
# written by Jeffrey Scherling Sun Mar  7 15:05:00 CET 2010
# append strings before or after strings in each line of given file 

BEF=""
AFT="yes"
STRING="-compat32"

FILE=$1
NEW="$FILE.new"

if [ "$BEF" = "yes" ] ; then
	cat $FILE | sed s/^/$STRING/ > $NEW

elif [ "$AFT" = "yes" ] ; then
	cat $FILE | sed s/$/$STRING/ > $NEW
fi


echo -e "\n Appending: \t\t [\033[1;35m  Finished  \033[0m]\n"

# -
