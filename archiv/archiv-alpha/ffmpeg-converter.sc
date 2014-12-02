#!/bin/sh
# converting flv to mpg or other things
# Usage script.sh videoname
#FILE=""

# flv to 3gp
#ffmpeg -i I_KRmU2dO2M.flv -ab 56 -ar 22050 -b 500 -s 320x240 video.mpg
#ffmpeg -i I_KRmU2dO2M.flv -ab 56 -ar 22050 -b 500 -s 320x240 -vcodec xvid -acodec mp3 video.avi
ffmpeg -i $1 -s 176×144 -vcodec h263 -r 25 -b 200 -ab 64 -acodec mp3 -ac 1 -ar 8000 output.3gp
#ffmpeg -i input.flv -s 176×144 -vcodec h263 -r 25 -b 200 -ab 64 -acodec libmp3lame -ac 1 -ar 8000 output.3gp
#ffmpeg -i InputVid.flv -s qcif -vcodec mpeg4 -r 10 -b 180 -sameq -ab 64 -acodec aac -ac 1 -ar 22050 OutputVid.mp4

# mpg to 3gp
#ffmpeg -i video_clip.mpg -s qcif -vcodec h263 -acodec mp3 -ac 1 -ar 8000 -ab 32 -y clip.3gp

# avi to 3gp
#ffmpeg -i video_clip.avi-s qcif -vcodec h263 -acodec mp3 -ac 1 -ar 8000 -r 25 -ab 32 -y clip.3gp

# 3gp to avi
#ffmpeg -i clip.3gp -f avi -vcodec xvid -acodec mp3 -ar 22050 file.avi
