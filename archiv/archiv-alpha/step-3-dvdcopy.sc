#!/bin/sh
#
# dvd anplexen

#echo "Location of AUDIO_TS  VIDEO_TS"
#read Location
echo "Filename [foobar]"
read NAME

# multiplexing
#tcmplex -i $NAME.new -p $NAME.ac3 -m d -o $NAME.vob
mplex -f 8 -o $NAME.vob $NAME.new $NAME.ac3 -S 0
# audio/video sync correction
#mplex -f 8 -O-5000ms -o $NAME.vob $NAME.new $NAME.ac3 -S 0
#rm $NAME.new

echo "#############################"
echo "  $NAME is multiplexed      "
echo "#############################"


