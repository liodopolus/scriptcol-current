#!/bin/bash


# Author
# Jeffrey Scherling Wed Dec 24 14:35:00 CET 2015


# Usage
# SARPI (Slackwarearm on Raspberry Pi) install script


# Sources 
# http://rpi.fatdog.eu/index.php?p=home


# Version
# 0.0.1 alpha


# Disclaimer
# It's Free Open Source Software.
# There is no Warranty against Damages, be careful!
# Execution of this Script is at your own risk!


# Todo
# 1. create transfer routine for system files

# first check like S1
# make filesystems for other partitions
# mkfs.xfs /dev/mmcblk0p3
# mkfs.xfs /dev/mmcblk0p4

# check if dir for mountpoint exist and umount and remove it after transfer of files

# 2. write routine for additional files and packages, see sarpi_packages


# Bash settings

#set -v # be verbose
#set -n # no execution
#set -e # exit on error, or if program exit on 1
#set -u # error for unset variables


# Variables

# SD Card device
SD="/dev/mmcblk0"

# SD Card identifier 
DI="0xceb97f35"

# SD CARD mountpoints
S1="mmcblk0p1"

# Boot files
BF="sarpi-boot-files"


# Functions

sdc () {

# SD CARD check

if [ ! -e ${SD} ] ;  then

	echo "   +-------------+"
	echo "   | No SD Card! |"
	echo "   +-------------+"

	exit 1

else

	echo "   +----------------+"
	echo "   | SD Card Ready! |"
	echo "   +----------------+"

fi

}


sdv() {

# SD Card verification

# get identifier
ID="$(sfdisk -l ${SD} | grep identifier)" 

# cut identifier with bash
ID="${ID:17:10}"

if [ ${DI} == ${ID:-""} ] ; then

	echo "   +----------------------+"
	echo "   | SARPI SD Card Ready! |"
	echo "   +----------------------+"
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

	echo "   +------------------------+"
	echo "   | Attention New SD Card! |"
	echo "   | You need 10G of Space! |"
	echo "   +------------------------+"
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


sdi() {

# SD Card infomations

	echo "   +----------------------+" 
	echo "   | SD CARD INFORMATIONS |"
	echo "   +----------------------+" 

echo ""
sfdisk -l ${SD}
echo ""

}


sdp() {

# SD Card partitioning

	echo "   +---------------------+" 
	echo "   | CREATING PARTITIONS |"
	echo "   +---------------------+" 

cat << TXT | sfdisk ${SD}

label: dos
label-id: 0xceb97f35
device: test.img
unit: sectors

 , 105mb, c, *
 , 524mb, S
 , 8G, L 
 , , L 

TXT

# wait for finish
wait

}


sdf() {

# SD Card filesystem

# get SD Card status
SU="$(pidof sfdisk || pidof fdisk || pidof cfdisk || pidof parted || pidof partitionmanager)" # partion in use
SM="$(df -h | grep ${S1})" # disk mounted

if [ -n "${SU:-""}" ] ; then
	
	echo "   +------------------------+" 
	echo "   | STOP PARTITION MANAGER |"
	echo "   +------------------------+" 

	exit 1

elif [ -n "${SM:-""}" ] ; then

	echo "   +-------------+" 
	echo "   | UMOUNT DISK |"
	echo "   +-------------+" 

	exit 1

elif [ -e "/dev/${S1}" ] ; then

	echo "   +---------------------+" 
	echo "   | CREATING FILESYSTEM |"
	echo "   +---------------------+" 

	# wait for finish
	wait

	# make filesystem vfat
	mkfs.vfat /dev/${S1}

else
	exit 1

fi

}


sdb() {

# SD Card bootfiles copy

if [ ! -d ${S1} ] ; then

	echo "   +--------------------------+" 
	echo "   | CREATING MOUNT DIRECTORY |"
	echo "   +--------------------------+" 
	
	# create mount directory
	mkdir -p ${S1}

	# wait for finish
	wait

fi

	echo "   +--------------------+" 
	echo "   | MOUNTING DIRECTORY |"
	echo "   +--------------------+" 

	# mount the mount directory
	mount /dev/${S1} ${S1}/

	# wait for finish
	wait

if [ -e "${BF}.tar.gz" ] ; then

	echo "   +----------------------+" 
	echo "   | UNPACKING BOOT FILES |"
	echo "   +----------------------+" 

	# unpack boot files
	tar xvzf "${BF}.tar.gz"

	# wait for finish
	wait

else 
	echo "   +---------------+" 
	echo "   | NO BOOT FILES |"
	echo "   +---------------+" 

	exit 1

fi


if [ -d ${BF} ] ; then

	echo "   +-----------------+" 
	echo "   | COPY BOOT FILES |"
	echo "   +-----------------+" 

	# copy bootfiles to mount directory
	mv ${BF}/* ${S1}/

	# wait for finish
	wait

	# remove bootfiles directory
	rm -r ${BF}/ 

	# umount boot partion
	umount ${S1}/

	# wait for finish
	wait

else

	echo "   +-------------------+" 
	echo "   | NO BOOT DIRECTORY |"
	echo "   +-------------------+" 

	exit 1

fi

}


sds() {

# SD Card systemfiles

	echo "Systemfiles" # similar to sdb	
}


# Commands

case $1 in

	sdc)
		sdc # SD Card check			stable
		;;
	sdv)
		sdc # SD Card check			stable
		sdv # SD Card verification		stable
		;;
	sdi)
		sdc # SD Card check			stable
		sdi # SD Card informations		stable
		;;
	sdp)
		sdc # SD Card check			stable
		sdv # SD Card verification		stable
		sdp # SD Card partitioning		stable
		;;
	sdf)
		sdc # SD Card check			stable
		sdv # SD Card verification		stable
		sdf # SD Card filesystem		stable
		;;
	sdb)
		sdc # SD Card check			stable
		sdv # SD Card verification		stable
		sdb # SD Card bootfiles copy		stable
		;;
	sds)
		sdc # SD Card check			stable
		sdv # SD Card verification		stable
		#sds # SD Card systemfiles copy		alpha
		;;
	*)
		# for all other cases

	echo ""
	echo "   usage: $(basename $0 .sc) CMD   "
	echo "                                   "
	echo "   CMD: DESCRIPTION                "
	echo "   sdc: SD CARD check              "
	echo "   sdv: SD CARD verification       "
	echo "   sdi: SD CARD informations       "
	echo "   sdp: SD CARD partitioning       "
	echo "   sdf: SD CARD filesystem         "
	echo "   sdb: SD CARD bootfiles copy     "
	echo "   sds: SD CARD systemfiles copy   "
	echo ""

	;;
esac

