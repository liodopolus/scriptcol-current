#!/bin/sh
# written by Jeffrey Scherling Thu Jul  1 19:49:21 CEST 2010
# extract mp3 from youtube flv/mp4 files

FLV=`echo *.flv`
MP4=`echo *.mp4`
#echo *.mp4 | cut -d . -f 2

for i in $FLV ; do
	ffmpeg -i $i -ab 128 -ar 44100 "`basename $i .flv`.mp3"
done

for i in $MP4 ; do
	ffmpeg -i $i -ab 128 -ar 44100 "`basename $i .mp4`.mp3"
done

# -
