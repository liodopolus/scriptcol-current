#!/bin/sh
# written by Jeffrey Scherling Sun Sep 12 21:18:05 CEST 2010
# start qemu with vm machines

CWD=$(pwd)

echo -e "\n Start QEMU-Windows-Image? [yes|no]:" ; read ANS1

if [ "$ANS1" = "yes" ] ; then
	NAME=windows.img
	SIZE=20G

        #qemu -cpu qemu32 -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -hda $NAME -m 768
        #qemu-system-x86_64 -cpu athlon -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -hda $NAME -m 768
        #qemu-system-x86_64 -cpu athlon -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga cirrus -hda $NAME -m 768
        qemu-system-x86_64 -cpu athlon -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -hda $NAME -m 768


elif [ "$ANS1" = "no" ] ; then
	echo -e "\n No Windows started"
	#exit 1
else
	exit 1
fi


echo -e "\n Start QEMU-Gentoo-Image? [yes|no]:" ; read ANS2

if [ "$ANS2" = "yes" ] ; then
	NAME=gentoo.img
	SIZE=20G

	#qemu-system-x86_64 -cpu qemu64 -smp 2 -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -hda $NAME -m 1024
	#qemu-x86_64 -cpu qemu64 -smp 2 -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -hda $NAME -m 768
	qemu-system-x86_64 -cpu -phenom -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -hda $NAME -m 768


elif [ "$ANS2" = "no" ] ; then
	echo -e "\n No Gentoo started"
	#exit 1
else
	exit 1
fi


echo -e "\n Start QEMU-Slackware64-Image? [yes|no]:" ; read ANS3

if [ "$ANS3" = "yes" ] ; then
	NAME=slackware64-current-qcow2.img
	SIZE=20G

	#qemu-system-x86_64 -cpu qemu64 -smp 2 -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -hda $NAME -m 1024
	#qemu-x86_64 -cpu qemu64 -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga cirrus -hda $NAME -m 768
	#qemu-system-x86_64 -cpu phenom -kernel-kqemu -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga cirrus -hda $NAME -m 768
	qemu-system-x86_64 -cpu phenom -soundhw ac97 -k de -localtime -usb -usbdevice tablet -net nic -net user -vga std -hda $NAME -m 2048 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n No Slackware64 started"
	#exit 1
else
	exit 1
fi


# - 
