#!/bin/sh
# written by Jeffrey Scherling
# qemu-kvm start script
# use clock=pit on kernel command qemu can not emulate so fast realtime clock

CWD=$(pwd)
#DAT="`date +%Y-%b-%d`"

# options
DIS="slackware64-current"
SIZ="40G"
DAT="2015-04"
EXT="raw"	#raw, qcow2
ISO="slackware64-current-dvd.iso"
IMG="$DIS-$SIZ-$DAT.$EXT"

set -e

create_img() {

	# create new image and install iso
	echo -e "\n Creating Slackware Image? [yes|no]:" ; read ANS3
	if [ "$ANS3" = "yes" ] ; then
	
	# create image (default)
	qemu-img create -f $EXT $IMG $SIZ

	#qcow2 (performance, but bigger file size)
	#qemu-img create -f qcow2 -o cluster_size=1024,preallocation=metadata $IMG $SIZ

elif [ "$ANS3" = "no" ] ; then
	echo -e "\n Nothing done"
else
	exit 1
fi

}


install_iso() {
	# boot from cd and install given iso in qemu image (default)
	qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $IMG -m 4096 -boot d 

	# cpu qemu64 (more compatible)
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom "$ISO" -vga std -hda $IMG -m 4096 -boot d 
	
	# boot from cd
	#qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user -cdrom /dev/cdrom -vga std -hda $IMG -m 4096 -boot d 

}


boot_img() {
	
	# boot installed image (for correct time in windows use -rtc base=localtime) (default)
	qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda $IMG -m 4096 

	# cpu qemu64 (more compatible)
	#qemu-kvm -enable-kvm -cpu qemu64 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda $IMG -m 4096 
}


create_overlay() {

	# create new overlay from base image
	qemu-img create -f qcow2 -o backing_file=$IMG "$(basename $IMG .raw).ovl" 

	# performance, but bigger file size
	#qemu-img create -f qcow2 -o preallocation=metadata,cluster_size=1024,backing_file=$IMG "$(basename $IMG .raw).ovl"

}


start_overlay() {

	# start new overlay from base image with second harddisk -hdb

	# cpu host most compatible (default)
	#qemu-kvm -enable-kvm -cpu host -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda "$(basename $IMG .raw).ovl" -hdb qemu-exchange-6G-2015-04.raw -m 4096

	# cpu qemu64 (more compatible) (performance)
	qemu-system-x86_64 -cpu qemu64,+ssse3,+sse4.1,+sse4.2,+x2apic -smp cores=2,threads=2,sockets=1 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -enable-kvm -boot order=c -drive file=$(basename $IMG .raw).ovl,index=0,media=disk,format=qcow2 -drive file=qemu-exchange-6G-2015-04.raw,index=1,media=disk,format=raw -vga qxl -m 2048 

	# cpu Sandy Brigde Intel Xeon (testing)
	#qemu-kvm -enable-kvm -cpu SandyBridge -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda "$(basename $IMG .raw).ovl" -hdb qemu-exchange-6G-2015-04.raw -m 4096

	# cpu kvm64 (testing)
	#qemu-kvm -enable-kvm -cpu kvm64 -soundhw all -k de -localtime -usb -usbdevice tablet -net nic -net user,smb=/tmp/ -vga std -hda "$(basename $IMG .raw).ovl" -hdb qemu-exchange-6G-2015-04.raw -m 4096

}


case "$1" in
	create_img)
		create_img
		;;
	install_iso)
		install_iso
		;;
	boot_img)
		boot_img
		;;
	create_overlay)
		create_overlay
		;;
	start_overlay)
		start_overlay
		;;
	*)
		echo ""
		echo "usage: $(basename $0) { create_img | install_iso | boot_img | create_overlay | start_overlay }"
		echo ""
		echo "create : create new raw image with os.iso installation"
		echo "overlay : create new overlay frome base image with os installed"
		echo "start : start new overlay frome base image with os installed"
		echo ""
esac


