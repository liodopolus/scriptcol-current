#!/bin/sh
# written by Jeffrey Scherling Wed Mar 23 06:36:39 CET 2011
# start slackware64-current with qemu

CWD=$(pwd)

#echo -e "\n Starting Slackware64-current Image? [yes|no]:" ; read ANS3

ANS3="yes"

if [ "$ANS3" = "yes" ] ; then
	NAME=slackware64-current-qcow2.img
	SIZE=50G

        # default
	qemu-system-x86_64 -enable-kvm -cpu qemu64 -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -hda $NAME -m 2048 

        # install
	# qemu-system-x86_64 -enable-kvm -cpu qemu64 -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom slackware_x86_64-13.1-mini-install.iso -vga std -hda $NAME -m 2048 -boot d 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n Nothing done "
	#exit 1
else
	exit 1
fi


# - 
