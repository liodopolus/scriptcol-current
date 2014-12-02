#!/bin/sh
# written by Jeffrey Scherling Sat Nov  6 03:22:17 CET 2010
# rsync windows_xp to qemu img

CWD=$(pwd)

#echo -e "\n Create QEMU-Windows-Image? [yes|no]:" ; read ANS1
ANS1="no"

if [ "$ANS1" = "yes" ] ; then
	NAME=windows_xp-backup-qemu.img
	SIZE=20G
	qemu-img create -f raw $NAME $SIZE
	wait

	if [ ! -e windows-xp-install.iso ] ; then
		echo -e "\n Be sure there is the windows-xp-install.iso  or cd \n"
	fi

        #qemu-system-x86_64 -cpu athlon -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -cdrom windows-xp-install.iso -hda $NAME -m 768 -boot d
        qemu-system-x86_64 -cpu athlon -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -cdrom windows-xp-install.iso -hda $NAME -m 768 -boot d

fi

#-------------------------------------------

# qemu backup image
NAME=windows_xp-backup-qemu.img

# windows device, be careful !
WDEV=/dev/sda1

# mount points
SRC=$CWD/xp-mount
DST=$CWD/xp-backup

# check if windows is mounted anywhere
TST=`mount | grep $WDEV | cut -b -9`

if [ "$TST" = "$WDEV" ] ; then
	umount -lv $WDEV
	wait
fi

# check directories for mountpoints
if [ ! -d  $SRC ] ; then
    mkdir $SRC
fi

if [ ! -d  $DST ] ; then
    mkdir $DST
fi

# mount qemu image
mount $NAME $DST -o loop,offset=32256 -t ntfs-3g
#mount $NAME $DST -o loop,offset=$((512*63)) -t ntfs-3g
wait

# mount windows
mount $WDEV $SRC
wait

# sync
rsync -avz --delete --progress --include "pagefile.sys" $SRC $DST

# umount mountpoints
umount -lv $SRC
wait
#umount -lv $DST
wait

# timestamp
DATE="`date +%Y-%m-%d_%H-%M`"
STMP="Last-Windows_xp-Backup-$DATE"
rm -f Last-Windows_xp-Backup-*
touch $STMP

echo -e "\n Backup of $WDEV: [ in $NAME ] \t [\033[1;35m OK \033[0m]"

# -
