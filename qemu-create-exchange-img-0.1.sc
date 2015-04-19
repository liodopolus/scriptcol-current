#!/bin/sh
# written by Jeffrey Scherling
# create qemu harddisk and install slackware
# use clock=pit on kernel command qemu can not emulate so fast realtime clock

CWD=$(pwd)
#DAT="`date +%Y-%b-%d`"

# options
DIS="qemu-exchange"
SIZ="6G"
DAT="2015-04"
EXT="raw"	#raw, qcow2
#ISO="slackware64-current-dvd.iso"
IMG="$DIS-$SIZ-$DAT.$EXT"

set -e

create_img() {
	# create new image and install iso

echo -e "\n Creating Slackware Image? [yes|no]:" ; read ANS3

if [ "$ANS3" = "yes" ] ; then

	
	#default
	qemu-img create -f $EXT $IMG $SIZ

	#qcow2 performance
	#qemu-img create -f qcow2 -o cluster_size=1024,preallocation=metadata $IMG $SIZ
	#wait

	# cd boot
	# qemu -cdrom /dev/cdrom -hda $IMG -m 2048 -boot d


	# default
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $IMG -m 2048 -boot d 
	# performance, but not compatible

	# use this !!!
	#qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $IMG -m 4096 -boot d 

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n Nothing done"
else
	exit 1
fi

}

#create_clone() {
	# create new overlay from base image
	#qemu-img create -b $IMG -f qcow2 "$(basename $IMG .raw)-overlay.ovl" 
#}


case "$1" in
	create)
		create_img
		;;
	clone)
		create_clone
		;;
	*)
		echo ""
		echo "usage: $(basename $0) { create | clone }"
		echo ""
		echo "create : create new raw image with os.iso installation"
		echo "clone : create new clone frome base image with os installed"
		echo ""
esac


