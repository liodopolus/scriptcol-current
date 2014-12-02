#!/bin/sh
# written by Jeffrey Scherling Wed Sep 26 20:08:55 CEST 2012
# start windows xp with qemu

CWD=$(pwd)

#echo -e "\n Starting Windows XP Image? [yes|no]:" ; read ANS3

ANS="yes"

if [ "$ANS" = "yes" ] ; then

	#default base image backing_file, change it accordingly
	#NAM="winxp-6G-2012-Oct-03.raw"

	# default clone file
	NAM="winxp-6G-2012-Oct-03-clone-2012-Oct-03.qcow2"

	# -cpu qemu64, more compatible
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -hda $NAM -m 2048
	# + samba in tmp
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda $NAM -m 2048
	# performance + samba
	#qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda $NAM -m 4096
	qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda $NAM -m 4096

elif [ "$ANS" = "no" ] ; then
	echo -e "\n Nothing done "
else
	exit 1
fi


