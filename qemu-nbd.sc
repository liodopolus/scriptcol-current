#!/bin/sh
# written by Jeffrey Scherling Sun Apr 19 16:23:12 CEST 2015
# nbd load, unload and qemu-nbd connect, disconnect script
# only for partion 1, more is currently not supported


CWD=$(pwd)

IMG=qemu-exchange-6G-2015-04.raw
DIR=exchange
DEV=/dev/nbd0
PAR=/dev/nbd0p1


load() {

# if not loaded then load it
if [ "$(lsmod | grep nbd | cut -b -3 )" != "nbd" ] ; then
	#echo "Module not loaded"

	# load nbd
	modprobe nbd max_part=63
	echo "Module loaded"

elif [ ! "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	echo "Module is connected"

elif [ "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	echo "Module is disconnected"

else
	echo "ERROR"
	exit 1
fi

}


rload() {

# if disconnected then reload it
if [ "$(lsmod | grep nbd | cut -b -3 )" != "nbd" ] ; then
	echo "Module not loaded"

elif [ ! "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	echo "Module is connected"

elif [ "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	#echo "Module is disconnected"

	# reload nbd
	rmmod nbd
	modprobe nbd max_part=63
	echo "Module reloaded"

else
	echo "ERROR"
	exit 1
fi

}


conn() {

# if disconnected then connect it
if [ "$(lsmod | grep nbd | cut -b -3 )" != "nbd" ] ; then
	echo "Module not loaded"

elif [ ! "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	echo "Module is connected"

elif [ "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	#echo "Module is disconnected"

	# connect nbd
	qemu-nbd -c $DEV $IMG
	echo "NBD connected"

else
	echo "ERROR"
	exit 1
fi

}


disco() {
# if connected then disconnect it
if [ "$(lsmod | grep nbd | cut -b -3 )" != "nbd" ] ; then
	echo "Module not loaded"

elif [ ! "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	#echo "Module is connected"

	# disconnect nbd
	qemu-nbd -d $DEV
	echo "NBD disconnected"

elif [ "$(lsmod | grep nbd | cut -b 31)" -eq 0 ] ; then
	echo "Module is disconnected"

else
	echo "ERROR"
	exit 1
fi

}


mount() {

# if not mounted then mount
if [ "$(df -h | grep nbd | cut -b -11)" != $PAR ] ; then
	#echo "$PAR not mounted"

# if dir exist then mount
if [ ! -d $DIR ] ; then
	echo ""
	echo "Directory: [ $DIR ] does not exist"
	echo ""

elif [ -d $DIR ] ; then

	# mount first partion in image
	sudo -u root mount $PAR $DIR
	echo "Partition 1 mounted"

else
	echo "ERROR"
	exit 1
fi

elif [ "$(df -h | grep nbd | cut -b -11)" = $PAR ] ; then
	echo "$PAR mounted"

else
	echo "ERROR"
	exit 1
fi

}


umount() {

# if mounted then umount

if [ "$(df -h | grep nbd | cut -b -11)" != $PAR ] ; then
	echo "$PAR not mounted"

elif [ "$(df -h | grep nbd | cut -b -11)" = $PAR ] ; then
	#echo "$PAR mounted"

	# umount partition
	sudo -u root umount $PAR
	echo "Partition 1 umounted"

else
	echo "ERROR"
	exit 1
fi

}


case "$1" in
	-load)
		load
		;;
	-rload)
		rload
		;;
	-conn)
		conn
		;;
	-disco)
		umount # first umount
		disco # then disconnect
		;;
	-mount)
		load # first load
		conn # first connect
		mount # then mount
		;;
	-umount)
		umount
		;;
	*)
		echo ""
		echo "usage: $(basename $0) -option"
	      	echo "-load"
		echo "-rload" 
		echo "-conn"
	       	echo "-disco" 
		echo "-mount"
		echo "-umount" 
		echo ""

		exit 1
esac

