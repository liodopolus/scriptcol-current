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
#tcrequant -f 1.2 -i $NAME.m2v -o $NAME.new
tcrequant -f 1.4 -i $NAME.m2v -o $NAME.new

echo "#############################"
echo "  $NAME was requantisiert    "
echo "#############################"

