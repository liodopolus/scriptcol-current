#!/bin/sh
# written by Jeffrey Scherling
# scale all images in CWD and put these in scaled-images

CWD=$(pwd)

OUTPUT="converted-images"

if [ ! -d $OUTPUT ]; then
	echo "Directory [ $OUTPUT ] does'nt exist"
	mkdir $CWD/$OUTPUT
else
	echo "Directory [ $OUTPUT ] exist"
	exit 1
fi



