#!/bin/sh
# written by Jeffrey Scherling Sat Sep 29 10:53:30 CEST 2012
# create qemu harddisk and install winxp from iso
# use clock=pit on kernel command qemu can not emulate so fast realtime clock

CWD=$(pwd)
DAT="`date +%Y-%b-%d`"

set -e

xp_inst() {
	# create new image and install iso

echo -e "\n Creating WinXP Image? [yes|no]:" ; read ANS3

if [ "$ANS3" = "yes" ] ; then

	# options
	DIS="winxp"
	SIZ="6G"
	EXT="raw"	#raw, qcow2
	ISO="windows-xp-install.iso"
	NAM="$DIS-$SIZ-$DAT.$EXT"

	#default
	qemu-img create -f $EXT $NAM $SIZ

	#qcow2 performance
	#qemu-img create -f qcow2 -o cluster_size=1024,preallocation=metadata $NAM $SIZ
	wait

	# cd boot
	# qemu -cdrom /dev/cdrom -hda $NAM -m 2048 -boot d

	# default
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $NAM -m 2048 -boot d 
	# performance, but not compatible
	qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $NAM -m 4096 -boot d 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n Nothing done"
else
	exit 1
fi

}

xp_clone() {
	# create new clone from base image

	# base image
	BKF="winxp-6G-2012-Oct-03.raw"

	qemu-img create -b $BKF -f qcow2 "$(basename $BKF .raw)-clone-$DAT.qcow2" 
}


case "$1" in
	xp_inst)
		xp_inst
		;;
	xp_clone)
		xp_clone
		;;
	*)
		echo ""
		echo "usage: $(basename $0) { xp_inst | xp_clone }"
		echo ""
		echo "xp_inst : create new raw image with xp installation"
		echo "xp_clone : create new clone frome base image"
		echo ""
esac


