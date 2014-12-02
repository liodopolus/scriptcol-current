#!/bin/sh
# written by Jeffrey Scherling Tue Jan  3 02:10:59 CET 2012
# converting smartphone movies for facebook to flv
# usage script + videoname

# formats
# PAL (wide)    1024x576  SAR 16:9    DAR 16:9  PAR 1:1    Hz 25 Mpx/s 14,7
# PAL (opti)    960x540   SAR 16:9    DAR 16:9  PAR 1:1    Hz 25 Mpx/s 13,0

# NTSC (VCD)    352x240   SAR 11:15   DAR  4:3  PAR 0,909  Hz 30 Mpx/s 2,5
# NTSC (VCD)    352x240   SAR 11:15   DAR 16:9  PAR 1,1212 Hz 30 Mpx/s 2,5

# NTSC (SVCD)   480x480   SAR  1:1    DAR  4:3  PAR  4:3   Hz 30 Mpx/s 6,9
# NTSC (SVCD)   480x480   SAR  1:1    DAR 16:9  PAR 16:9   Hz 30 Mpx/s 6,9

# NTSC          544x480   SAR 17:15   DAR  4:3  PAR 1.176  Hz 30 Mpx/s 7,8
# NTSC          544x480   SAR 17:15   DAR 16:9  PAR 1.568  Hz 30 Mpx/s 7,8

# NTSC (VGA)    640x480   SAR  4:3    DAR  4:3  PAR 1:1    Hz 30 Mpx/s 9,2

# NTSC (DVD)    704x480   SAR 22:15   DAR  4:3  PAR 0,909  Hz 30 Mpx/s 10,1
# NTSC (DVD)    704x480   SAR 22:15   DAR 16:9  PAR 1,212  Hz 30 Mpx/s 10,1

# HD720         960x720   SAR  4:3    DAR  4:3  PAR 1:1    Hz 50 Mpx/s 34,6
# HD720  (HD)   1280x720  SAR 16:9    DAR 16:9  PAR 1:1    Hz 50 Mpx/s 46,1

# HD1080        1440x1080 SAR  4:3    DAR  4:3  PAR 1:1    Hz 25 Mpx/s 38,9
# HD1080 (HD)   1920x1080 SAR 16:9    DAR 16:9  PAR 1:1    Hz 25 Mpx/s 51,8

# 3gp 128x96, 176x144, 352x288, 704x576, and 1408x1152. Try H.263+


SRC=$1
TAR="out.$SRC"


# flv hd480 - 852x480
#ffmpeg -i $SRC -f flv -s hd480 -b 2000k -ar 44100 $TAR.flv

# flv hd720 - 1280x720
ffmpeg -i $SRC -f flv -s hd720 -b 2000k -ar 44100 $TAR.flv

# flv hd1080 - 1920x1080
#ffmpeg -i $SRC -f flv -s hd1080 -b 2000k -ar 44100 $TAR.flv


# - 
