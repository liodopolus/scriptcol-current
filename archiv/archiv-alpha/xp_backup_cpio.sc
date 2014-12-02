#!/bin/sh
#Backup of XP on /dev/sda1 /mnt/xp with ntfs-3g Mountpoint

# Directories
CWD=`pwd`
SDIR="$CWD/source"
DFILE="xp_bck_file.cpio"

# mount source
mkdir $SDIR
wait
mount -t ntfs-3g /dev/sda1 $SDIR -o force
wait

# make backup of SDIR
#echo "--- Start Backup of $SDIR ---"
#find $SDIR -print0 -depth | cpio -o -0aV -H newc --quiet > $DFILE
#echo "--- Backup in [$DFILE] ---"

# list content of DFILE
#echo "--- List Content of [$DFILE] ---"
#cpio -i -t --quiet < $DFILE 
#echo "--- End of Listing Content of [$DFILE] ---"

# extract content of DFILE
echo "--- Extract Content of [$DFILE] ---"
##cpio -i -bdfmv --quiet < $DFILE
cpio -i -F $DFILE -dfmu --quiet
echo "--- End Extracting of [$DFILE] ---"

