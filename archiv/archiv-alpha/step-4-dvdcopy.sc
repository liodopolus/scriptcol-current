#!/bin/sh
# dvd-copy-2
#
# dvd requantisieren, multiplexen und brennen

#echo "Location of AUDIO_TS  VIDEO_TS"
#read Location
echo "Filename [foobar]"
read NAME

# requantisieren, dabei mit 4.1G rechnen (sicherheitshalber)
#tcrequant -f 1.3 -i $NAME.m2v -o $NAME.new
#tcrequant -f 1.28 -i $NAME.m2v -o $NAME.new
#tcrequant -f 1.2 -i $NAME.m2v -o $NAME.new
#tcrequant -f 1.16 -i $NAME.m2v -o $NAME.new


# multiplexing
#tcmplex -i $NAME.new -p $NAME.ac3 -m d -o $NAME.vob
#mplex -f 8 -o $NAME.vob $NAME.new $NAME.ac3 -S 0
# audio/video sync correction
#mplex -f 8 -O-3600ms -o $NAME.vob $NAME.new $NAME.ac3 -S 0
#rm $NAME.new

# dvd-structure
dvdauthor -t -a ac3+de -o dvd-structure $NAME.vob

# ifo-files
dvdauthor -T -o dvd-structure 

# burning
growisofs -dvd-compat -speed=4 -Z /dev/dvd -dvd-video dvd-structure

# eject
echo "#############################"
echo "  $NAME was burned to DvD    "
echo "#############################"
eject dvd
#/home/jack/scripts/shutdown.sc


