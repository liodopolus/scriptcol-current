#!/bin/sh
# ogg to wav and wav to mp3 script
# best standard options for lame optional (fast standard)

# all ogg to wav
for OGG in ls *.ogg ; do oggdec $OGG ; done 

# all wav to mp3
for WAV in ls *.wav ; do lame --preset standard $WAV ; done 

# rename all wav.mp3 to mp3
for file in ls *.mp3 ; do mv $file `echo $file | sed s/wav.mp3/mp3/` ; done 


echo "Delete now all Wav"
echo "Delete now all Wav"
echo "Delete now all Wav"
