#!/bin/sh
# convert wma to mp3

current_directory=$( pwd )

# remove spaces
for j in *.[Ww][Mm][Aa] ; do mv "$j" `echo $j | tr ' ' '-'` ; done

# uppercase to lowercase
#for j in *.[Ww][Mm][Aa] ; do mv "$j" `echo $j | tr '[A-Z]' '[a-z]'` ; done

# rip with Mplayer / encode with LAME
#for j in *.wma ; do mplayer -vo null -vc dummy -af resample=44100 -ao pcm:waveheader $j && lame -m s audiodump.wav -o $j ; done
for j in *.wma ; do mplayer -vo null -vc dummy -af resample=44100 -ao pcm:waveheader $j && lame --preset standard audiodump.wav -o $j ; done

#1 rip with Mplayer to wav
#for j in *.wma ; do mplayer -vo null -vc dummy -af resample=44100 -ao pcm:waveheader:file="`basename "$j" .wma`.wav" $j ; done 

#2 encode wav to mp3 with lame and rename
#for j in *.wav ; do lame --preset fast standard $j -o "`basename "$j" .wav`.mp3" ; done

# convert file names wma to mp3
for j in *.wma ; do mv "$j" "`basename "$j" .wma`.mp3" ; done

rm audiodump.wav

# insert spaces
for j in *.mp3 ; do mv "$j" "`echo $j | tr '-' ' '`" ; done



