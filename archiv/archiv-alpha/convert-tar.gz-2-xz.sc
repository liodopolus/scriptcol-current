#!/bin/sh
# written by Jeffrey Scherling Tue Mar 23 05:30:43 CET 2010
# convert tar.gz to xz format

for i in `echo *.tar.gz` ; do
	gzip -d $i
	xz -z `basename $i .gz`
done
