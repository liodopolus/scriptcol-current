#!/bin/sh
# written by Jeffrey Scherling Sun Aug 29 18:43:30 CEST 2010
# create tar archive from directories and compress them with xz

tar -cvf - $1 | xz - > `basename $1 /`.tar.xz

# -
