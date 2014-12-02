#!/bin/sh
# written by Jeffrey Scherling Tue Mar 22 18:42:29 CET 2011
# create qemu harddisk and install slackware64-current from iso
# use clock=pit on kernel command qemu can not emulate so fast realtime clock

CWD=$(pwd)

echo -e "\n Creating Slackware64-current Image? [yes|no]:" ; read ANS3

if [ "$ANS3" = "yes" ] ; then
	NAME=slackware64-current-qcow2.img
	SIZE=50G
	qemu-img create -f qcow2 $NAME $SIZE
	wait

	# cd boot
	# qemu -cdrom /dev/cdrom -hda $NAME -m 2048 -boot d

	# iso boot
	# qemu-system-x86_64 -cpu phenom -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom slackware64-current-install-dvd.iso -vga std -hda $NAME -m 2048 -boot d 
	qemu-system-x86_64 -enable-kvm -cpu qemu64 -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom slackware_x86_64-13.1-mini-install.iso -vga std -hda $NAME -m 2048 -boot d 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n Nothing done"
else
	exit 1
fi

# -
