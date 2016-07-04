#!/bin/bash
# written by Jeffrey Scherling
# Sat Jan  9 12:50:17 CET 2016


# Usage
# rsync beta_private with 29 GB USB Sparky


# Version
# 0.0.1


# Disclaimer
# It's Free Open Source Software.
# There is no Warranty against Damages, be very careful!
# Execution of this Script is at your own risk!


# Todo
# see Functions


# Bash settings
#set -v # be verbose
#set -n # no execution
#set -e # exit on error, or if program exit on 1
#set -u # error for unset variables


# Variables

# source
SRC="/media/gamma_private/beta_private"

# target
TAR="/media/sparky/"

# USB device
USBD="/dev/sdb"

# USB identifier 
USBI="0x3b1698de"

# USB mountpoints
USB1="sdb1"


# === Don't change anything below this line,if you don't know what are you doing === #


# Functions

usbc () {

# USB check

if [ ! -e ${USBD} ] ;  then

	echo "   +--------------------+"
	echo "   | No USB (/dev/sdb)! |"
	echo "   +--------------------+"

	exit 1

else

	echo "   +-----------------------+"
	echo "   | USB (/dev/sdb) Ready! |"
	echo "   +-----------------------+"

fi

}


usbv() {

# USB verification

# get identifier
ID="$(sfdisk -l ${USBD} | grep identifier)" 

# cut identifier with bash
ID="${ID:17:10}"

if [[ ${USBI} == ${ID:-""} ]] ; then

	echo "   +----------------------------+"
	echo "   | USB 29 GB Sparky verified! |"
	echo "   +----------------------------+"
	echo "   +----------------------------+"
	echo "   | Do you want continue (y|n) |" 
	echo "   +----------------------------+" ; read ANS
	
	if [ ${ANS:-""} != "y" ] ; then
		echo ""
		echo "ABORTET"
		echo ""
		exit 1
	fi

else

	echo "   +--------------------------------------------------------------------------+"
	echo "   | Attention New Block Device be very careful, otherwise Data will be lost! |"
	echo "   | Please insert 29 GB USB Sparky!                                          |"
	echo "   +--------------------------------------------------------------------------+"
	echo "   +----------------------------+"
	echo "   | Do you want continue (y|n) |"
	echo "   +----------------------------+" ; read ANS
	
	if [ ${ANS:-""} != "y" ] ; then
		echo ""
		echo "ABORTET"
		echo ""
		exit 1
	fi

fi

}


usbi() {

# USB infomations

	echo "   +------------------+" 
	echo "   | USB INFORMATIONS |"
	echo "   +------------------+" 

echo ""
sfdisk -l ${USBD}
echo ""

}


usbp() {

# USB partitioning

	echo "   +---------------------+" 
	echo "   | CREATING PARTITIONS |"
	echo "   +---------------------+" 

# for testing use sfdisk -n (--no-act) no change of partitons
cat << TXT | sfdisk ${USBD}

label: dos
label-id: ${USBI}
unit: sectors

 , , L 

TXT

# wait for finish
wait

}


usbf() {

# USB filesystem

# get USB status
USBU="$(pidof sfdisk || pidof fdisk || pidof cfdisk || pidof parted || pidof partitionmanager)" # partion in use
USBM="$(df -h | grep ${USB1})" # disk mounted

if [ -n "${USBU:-""}" ] ; then
	
	echo "   +------------------------+" 
	echo "   | STOP PARTITION MANAGER |"
	echo "   +------------------------+" 

	exit 1

elif [ -n "${USBM:-""}" ] ; then

	echo "   +-------------+" 
	echo "   | UMOUNT DISK |"
	echo "   +-------------+" 

	exit 1

elif [ -e "/dev/${USB1}" ] ; then

	echo "   +---------------------+" 
	echo "   | CREATING FILESYSTEM |"
	echo "   +---------------------+" 

	# wait for finish
	wait

	# make filesystem xfs
	# for testing use mkfs.xfs -f -N (no action)
	mkfs.xfs -f /dev/${USB1}

else
	exit 1

fi

}


usbs() {

# USB syncing with rsync

if [ ! -d ${TAR} ] ; then

	echo "   +--------------------------+" 
	echo "   | CREATING MOUNT DIRECTORY |"
	echo "   +--------------------------+" 
	
	# create mount/target directory
	mkdir -p ${TAR}

	# wait for finish
	wait

fi

	echo "   +--------------------+" 
	echo "   | MOUNTING DIRECTORY |"
	echo "   +--------------------+" 

	# mount partition (/dev/sdb1) to TAR
	mount /dev/${USB1} ${TAR}

	# wait for finish
	wait


# check target and source
if [[ -d ${TAR}  &&  -d ${SRC} ]] ; then

	echo "   +-------------+" 
	echo "   | RSYNC FILES |"
	echo "   +-------------+" 

	# rsync files
	# for testing use rsync -n (--dry-run) no changes will be made
	rsync -av --delete --progress ${SRC} ${TAR}

	# wait for finish
	wait

	# sync ram to disk
	sync

	# wait for finish
	wait

	# umount boot partion
	umount /dev/${USB1}

	# wait for finish
	wait

else

	echo "   +--------------------+" 
	echo "   | NO RSYNC DIRECTORY |"
	echo "   +--------------------+" 

	exit 1

fi

}


# Commands

case $1 in

	usbc)
		usbc # USB Sparky check		stable
		;;
	usbv)
		usbc # USB Sparky check		stable
		usbv # USB Sparky verification	stable
		;;
	usbi)
		usbc # USB Sparky check		stable
		usbi # USB Sparky informations	stable
		;;
	usbp)
		usbc # USB Sparky check		stable
		usbi # USB Sparky informations	stable
		usbv # USB Sparky verification	stable
		usbp # USB Sparky partitioning	stable
		;;
	usbf)
		usbc # USB Sparky check		stable
		usbi # USB Sparky informations	stable
		usbv # USB Sparky verification	stable
		usbf # USB Sparky formatting	stable
		;;
	usbs)
		usbc # USB Sparky check		stable
		usbi # USB Sparky informations	stable
		usbv # USB Sparky verification	stable
		usbs # USB Sparky syncing	stable
		;;
	*)
		# for all other cases

	echo ""
	echo "   usage: $(basename $0 .sc) CMD   "
	echo "                                   "
	echo "   CMD: DESCRIPTION                "
	echo "   usbc: USB Sparky check          "
	echo "   usbv: USB Sparky verification   "
	echo "   usbi: USB Sparky informations   "
	echo "   usbp: USB Sparky partitioning   "
	echo "   usbf: USB Sparky formatting xfs "
	echo "   usbs: USB Sparky syncing        "
	echo ""

	;;
esac

