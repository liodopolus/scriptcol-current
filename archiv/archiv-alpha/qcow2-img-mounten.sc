#!/bin/sh
# written by Jeffrey Scherling Wed Mar 23 21:14:14 CET 2011
# mount qemu qcow2 image with nbd

CWD=$(pwd)

NBD=`lsmod | grep nbd | cut -b -3`

SRC="slackware64-current-qcow2.img"
TAR="mountpoint"

# load nbd module
if [ ! "nbd" = "$NBD" ] ; then
	echo -e "\n Loading NBD-Module \n"
	modprobe nbd max_part=8
	sleep 1s
fi

if [ ! -d $TAR ] ; then
	mkdir $CWD/$TAR
fi

# connect nbd to qcow2 image
qemu-nbd --connect=/dev/nbd0 $CWD/$SRC
sleep 1s

# mount image
mount /dev/nbd0p1 $CWD/$TAR

# only for disconnect
# qemu-nbd -d /dev/nbd0

# - 
