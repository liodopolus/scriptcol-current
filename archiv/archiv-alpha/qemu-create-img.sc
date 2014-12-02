#!/bin/sh
# written by Jeffrey Scherling Sun Sep 12 21:18:05 CEST 2010
# create qemu harddisk and install os

CWD=$(pwd)

echo -e "\n Create QEMU-Windows-Image? [yes|no]:" ; read ANS1

if [ "$ANS1" = "yes" ] ; then
	NAME=windows.img
	SIZE=20G
	qemu-img create -f qcow2 $NAME $SIZE
	wait

	#put cd into drive and run
	#qemu -cdrom /dev/cdrom -hda $NAME -m 256 -boot d

	# be sure there is the os-install.iso
        #qemu-system-x86_64 -cpu athlon -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -cdrom windows-xp-install.iso -hda $NAME -m 768 -boot d
        qemu-system-x86_64 -cpu athlon -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -cdrom windows-xp-install.iso -hda $NAME -m 768 -boot d

elif [ "$ANS1" = "no" ] ; then
	echo -e "\n No Windows-Image created"
	#exit 1
else
	exit 1
fi


echo -e "\n Create QEMU-Gentoo-Image? [yes|no]:" ; read ANS2

if [ "$ANS2" = "yes" ] ; then
	NAME=gentoo.img
	SIZE=20G
	qemu-img create -f qcow2 $NAME $SIZE
	wait

	#put cd into drive and run
	#qemu -cdrom /dev/cdrom -hda $NAME -m 256 -boot d

	# be sure there is the os-install.iso
	qemu-system-x86_64 -cpu qemu64 -smp 2 -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom install-amd64-minimal-20100902.iso -vga std -hda $NAME -m 384 -boot d

elif [ "$ANS2" = "no" ] ; then
	echo -e "\n No Gentoo-Image created"
	#exit 1
else
	exit 1
fi


echo -e "\n Create QEMU-Slackware64-Image? [yes|no]:" ; read ANS3

if [ "$ANS3" = "yes" ] ; then
	NAME=slackware64-current-qcow2.img
	SIZE=20G
	qemu-img create -f qcow2 $NAME $SIZE
	wait

	#put cd into drive and run
	#qemu -cdrom /dev/cdrom -hda $NAME -m 256 -boot d

	# be sure there is the os-install.iso
	#qemu-system-x86_64 -cpu phenom -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom slackware_x86_64-13.1-mini-install.iso -vga std -hda $NAME -m 768 -boot d 
	qemu-system-x86_64 -cpu phenom -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom slackware64-current-install-dvd.iso -vga std -hda $NAME -m 2048 -boot d 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n No Slackware64-Image created"
	#exit 1
else
	exit 1
fi


# -
