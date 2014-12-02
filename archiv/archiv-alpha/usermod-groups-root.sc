#!/bin/sh
# written by Jeffrey Scherling Fri Feb 25 21:17:56 CET 2011
# add groups to root

USR="root"
GRP="root,bin,daemon,sys,adm,disk,wheel,floppy,audio,video,cdrom,tape,mysql,apache,plugdev,power,netdev,scanner,pulse,wlan"

usermod -a -G $GRP $USR

# -
