#!/bin/sh
# ---js---#
# create swap file
# usage: script + path to file enter check TARGET and fstab !
# e.g. ./script /mnt/daten/swapfile

TARGET="." # default directory is current directory
TARGET=$1 # 1. argument to script

dd if=/dev/zero of=$TARGET bs=1024 count=1024000
wait
sync # synchronise filesystems and flush buffers

mkswap -c $TARGET # format swapfile
swapon $TARGET # turn on swapfile 

echo -e "\n swapfile finished and ready to use\n"
echo -e "add <$TARGET \t none \t swap \t sw> to fstab \n" 
echo -e "<swapoff $TARGET> umount the swapfile\n"

free # shows the free memory of swapfile

# ---js---#
