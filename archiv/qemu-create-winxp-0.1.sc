#!/bin/sh
# written by Jeffrey Scherling Sun Sep 23 00:28:59 CEST 2012
# create qemu harddisk and install winxp from iso
# use clock=pit on kernel command qemu can not emulate so fast realtime clock

CWD=$(pwd)
DATE="`date +%Y-%b-%d`"

echo -e "\n Creating WinXP Image? [yes|no]:" ; read ANS3

if [ "$ANS3" = "yes" ] ; then

	# options
	SIZE="4G"
	OS="winxp"
	FRM="raw"	#raw, qcow2
	NAME="$OS-$SIZE-$DATE-$FRM.img"

	#default
	qemu-img create -f $FRM $NAME $SIZE

	#qcow2 performance
	#qemu-img create -f qcow2 -o cluster_size=1024,preallocation=metadata $NAME $SIZE
	wait

	# cd boot
	# qemu -cdrom /dev/cdrom -hda $NAME -m 2048 -boot d

	# iso boot
	ISO="windows-xp-install.iso"

	# default
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $NAME -m 2048 -boot d 
	# performance, but not compatible
	qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $NAME -m 4096 -boot d 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n Nothing done"
else
	exit 1
fi

