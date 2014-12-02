#!/bin/sh
# wav to mp3 decoder
for j in *.wav ; do lame --preset standard $j -o "`basename "$j" .wav`.mp3" ; done 


