#!/bin/sh
# dvd-copy-1
#
# dvd kopieren ohne requantisieren

lsdvd /dev/dvd
#echo "Location of AUDIO_TS  VIDEO_TS"
#read Location
#lsdvd $Location

echo "Select Title [1,2,..]"
read TITLE
echo "Filename [foobar]"
read NAME

# videostream
mplayer -dvd-device /dev/dvd dvd://$TITLE -dumpstream -dumpfile /dev/stdout | tcextract -t vob -a 0 -x mpeg2 > $NAME.m2v
####mplayer -dvd-device $Location dvd://$TITLE -dumpstream -dumpfile /dev/stdout | tcextract -t vob -a 0 -x mpeg2 > $NAME.m2v

# audiostream
#mplayer -dvd-device /dev/dvd dvd://$TITLE -alang de -dumpaudio -dumpfile $NAME.ac3
#mplayer -dvd-device /dev/dvd dvd://$TITLE -aid 130 -dumpaudio -dumpfile $NAME.ac3
#mplayer -dvd-device /dev/dvd dvd://$TITLE -aid 129 -dumpaudio -dumpfile $NAME.ac3
mplayer -dvd-device /dev/dvd dvd://$TITLE -aid 128 -dumpaudio -dumpfile $NAME.ac3
####mplayer -dvd-device $Location dvd://$TITLE -aid 128 -dumpaudio -dumpfile $NAME.ac3

# als avi
#mencoder -dvd-device $Location dvd://$TITLE -o $NAME.avi -aid 128 -oac copy -ovc copy

# audio
#mplayer police.avi -dumpaudio -dumpfile test.ac3


# eject
echo "#############################"
echo "  $NAME was copied to disc  "
echo "#############################"
eject dvd
#/home/jack/scripts/shutdown.sc
