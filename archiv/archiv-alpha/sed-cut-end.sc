#!/bin/sh
# written by Jeffrey Scherling Sun Sep 18 18:04:07 CEST 2011
# cut the end with sed

CWD=$(pwd)

# String
STR="$1"

# Filename
FILE=$2

cat $FILE | sed s/$STR*.*/$STR/ > $FILE.without-end

# -
