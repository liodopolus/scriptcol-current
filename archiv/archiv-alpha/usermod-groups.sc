#!/bin/sh
# written by Jeffrey Scherling Tue Aug 24 20:40:10 CEST 2010
# add groups to an user

USR="jeff"
GRP="lp,floppy,audio,video,cdrom,kqemu,mysql,apache,plugdev,power,netdev,scanner,pulse,vboxusers,kvm,wlan"
usermod -a -G $GRP $USR

# -
