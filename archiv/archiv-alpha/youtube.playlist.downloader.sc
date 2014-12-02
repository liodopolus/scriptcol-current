#!/bin/sh
# written by Jeffrey Scherling Sun Aug 29 21:58:51 CEST 2010
# download videos from youtube with cclive from given playlist.Artist
# and convert them to mp3 files

FILE=`echo playlist.*`

for i in `grep http $FILE` ; do
	# ogg format
	#cclive -c -F "%t.%s" -r "/(\w|\s)/g" -S "s/\s/_/g" --exec="ffmpeg -i %i -acodec libvorbis -ab 128kb -ar 44100 %i.ogg;" -e $i

	# mp3 format
	cclive -c -F "%t.%s" -r "/(\w|\s)/g" -S "s/\s/_/g" --exec="ffmpeg -i %i -acodec libmp3lame -ab 128kb -ar 44100 %i.mp3;" -e $i
done


# -
