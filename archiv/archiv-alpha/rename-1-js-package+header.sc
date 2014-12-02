#!/bin/sh
# written by Jeffrey Scherling Thu Jul 22 17:27:09 CEST 2010
# rename package and header for 1-js packages in /var/log/packges from <1-js> to <1js> 

FILE=`echo *-js`

for i in $FILE ; do
	cat $i | sed s/x86_64-1-js/x86_64-1js/ > "`echo $i | sed s/-js/js/`"
	rm $i
done

# -
