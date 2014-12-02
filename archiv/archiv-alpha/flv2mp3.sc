#!/bin/sh
# written by Jeffrey Scherling Mon Apr 19 23:23:02 CEST 2010
# extract mp3 from flv (flash)

ffmpeg -i $1 -ab 128 -ar 44100 "`basename $1 .flv`.mp3"

# -
