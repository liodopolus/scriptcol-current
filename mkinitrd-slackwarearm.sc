#!/bin/bash
# create initrd of slackwarearm minirootfs

CWD=$(pwd)
SRC="$CWD/slack-14.1-miniroot"


#KVER=2.6.22.18-88f6281

# kernel version for modules to search
# -k $KVER \
# modules to include
#-m ext3:xfs:btrfs \
# include udev
# -u
# lvm support
# -L
# Raid support
# -R
# root filesystem together with -r
# -f xfs \
# root device
# -r /dev/sda2 \
# output
# -o $CWD/initrd-arm-$KVER.gz

mkinitrd -c \
	 -u \
	 -s $SRC \
	 -o $CWD/initrd-arm.gz

