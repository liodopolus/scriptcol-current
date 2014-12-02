#!/bin/sh
# avi2flv
# usage script + file + enter

FILE=$1
OUTMP4="`basename $FILE .avi`.mp4" 

#ffmpeg -y -i $FILE -acodec mp3 -ar 22050 -f flv $OUT
#ffmpeg -y -i $FILE -b 800 -r 25 -f flv -vcodec flv -ab 128 -ar 44100 $OUT
#mencoder -forceidx -of lavf -oac mp3lame -lameopts abr:br=56 -srate 22050 -ovc lavc -lavcopts vcodec=flv:vbitrate=250:mbd=2:mv0:trell:v4mv:cbp:last_pred=3 -vf scale=360:240 -o $1.flv $1
#mencoder -of lavf -oac mp3lame -lameopts abr:br=56 -srate 22050 -ovc lavc -lavcopts vcodec=flv:vbitrate=500:mbd=2:mv0:trell:v4mv:cbp:last_pred=3 $FILE -o $OUT

#ffmpeg -y -i $FILE -b 768 -s 320x240 -vcodec xvid -ab 128 -acodec aac -ac 2 -ab 64 -f mp4 out-$FILE

# i.O.
#ffmpeg -y -i $FILE -b 768 -s 320x240 -vcodec libx264 -ab 128k -acodec ac3 -ac 2 -ab 64k -f mp4 out-$FILE

#ffmpeg -y -i $FILE  -vcodec libx264 -ab 68k -acodec ac3 -ac 1 -ab 128k -f mp4 Tellerrand.avi



# video x264 - audio ac3 - container mp4
ffmpeg -y -i $FILE  -vcodec libx264 -acodec ac3 -f mp4 $OUTMP4


