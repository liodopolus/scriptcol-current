#!/bin/bash
# written by Jeffrey Scherling Sat Oct 24 11:36:50 CEST 2015
# search and move packages in directories


CWD=$(pwd)

# find packages
LIST=$(find $CWD -iname "*.t?z")

for PKG in ${LIST} ; do

	# list packages
	#echo ${PKG}

	# list packages without extension
	#echo ${PKG%.*}

	# crop leading directories
	TEMPFILE="${PKG##*/}"

	# list packages without leading directories
	#echo ${TEMPFILE}

	# list extensions
	#echo ${TEMPFILE##*.}

	# list packages without leading directories and  without extension
	#echo ${TEMPFILE%.*}

	# list package only with version
	TARGET=${TEMPFILE%-*-*"."*}
	#echo ${TARGET}

	# create directories for packages
	mkdir ${TARGET}

	# move packages to directories
	mv -fu ${PKG} ${TARGET}

	# move subfiles to directories
	mv -fu "${PKG%.*}.dep" ${TARGET}
	mv -fu "${PKG%.*}.lst" ${TARGET}
	mv -fu "${PKG%.*}.meta" ${TARGET}
	mv -fu "${PKG%.*}.txt" ${TARGET}
	mv -fu "${PKG%.*}.txz.asc" ${TARGET}
	mv -fu "${PKG%.*}.txz.md5" ${TARGET}

done


