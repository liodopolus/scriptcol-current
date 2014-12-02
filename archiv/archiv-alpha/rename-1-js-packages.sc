#!/bin/sh
# written by Jeffrey Scherling Thu Jul 22 17:27:09 CEST 2010
# rename <-js> packages in <js>

FILE=`echo *-js.t?z`

for i in $FILE ; do
	mv "$i" "`echo $i | sed s/-js/js/`"
done

# -
