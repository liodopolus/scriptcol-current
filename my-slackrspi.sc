#!/bin/sh
# written by Jeffrey Scherling Mon Mar 11 17:15:10 CET 2013
# create a full Raspberry Pi Slackware Image
# see this good guides
# http://wiki.gentoo.org/wiki/Raspberry_Pi_Quick_Install_Guide
# http://rpi.fatdog.eu/?p=home

IMG="slackrspi.img"
PAP="partitionpoints.tmp"

create_img()
# create image
{
dd if=/dev/zero of="$IMG" bs=1024k count=2000
}


create_par()
# create partition table for pi
{
echo "----------------------------------------------------------------------"
sfdisk -l $IMG
echo "----------------------------------------------------------------------"

echo ""
echo "ATTENTION ALL DATA WILL BE LOST"
echo "You want Create New Partion Table on $IMG ! [yes/no ]" ; read ANS1
echo ""

if [ "$ANS1" = "yes" ] ; then
       echo "Creating new Patition Table"

sfdisk  $IMG << EOF
-	7	c	*	
-	14	82	-
-	233	83	-
EOF

# verify partitions
sfdisk -V $IMG

elif [ "$ANS1" = "no" ] ; then

       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi
}


mount_par()
{
# mount partitions of image
partx -a -v $IMG | tail -1 | cut -d : -f -1 > $PAP
# echo partions
fdisk -l $(cat $PAP)"p1"
fdisk -l $(cat $PAP)"p2"
fdisk -l $(cat $PAP)"p3"
}


umount_par()
{
#LOOP="$(ls /dev/loop[0-9]*)"
#for i in $LOOP ; do
for i in $(cat $PAP) ; do
	partx -d -v $i"p1";
	partx -d -v $i"p2";
	partx -d -v $i"p3";
done
}


format_par()
{
# create filesystems data will be lost!
ls -al $(cat $PAP)*
# make vfat
echo ""
echo "ATTENTION ALL DATA WILL BE LOST"
echo "You want Format VFAT New Partion fdisk -l $(cat $PAP)"p1" ! [yes/no ]" ; read ANS2
echo ""

if [ "$ANS2" = "yes" ] ; then
	echo "Format new Patition Table"
	mkfs.vfat -F 16 $(cat $PAP)"p1"
elif [ "$ANS2" = "no" ] ; then
       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi


# make swap
echo ""
echo "ATTENTION ALL DATA WILL BE LOST"
echo "You want Format SWAP New Partion fdisk -l $(cat $PAP)"p2" ! [yes/no ]" ; read ANS3
echo ""

if [ "$ANS3" = "yes" ] ; then
	echo "Format new Patition Table"
	mkswap $(cat $PAP)"p2"
elif [ "$ANS3" = "no" ] ; then
       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi


# make ext4
echo ""
echo "ATTENTION ALL DATA WILL BE LOST"
echo "You want Format EXT4 New Partion fdisk -l $(cat $PAP)"p3" ! [yes/no ]" ; read ANS4
echo ""

if [ "$ANS4" = "yes" ] ; then
	echo "Format new Patition Table"
	mkfs.ext4 $(cat $PAP)"p3"

elif [ "$ANS4" = "no" ] ; then
       echo "Nothing Changed"
       exit 1
else
       echo "Nothing Changed"
       exit 1
fi

}


mount_points()
{
# create mountpoints and mount filesystems
$(mkdir -p {mnt-p1,mnt-p3})
wait
mount $(cat $PAP)"p1" mnt-p1
mount $(cat $PAP)"p3" mnt-p3
}


umount_points()
{
# umount mountpoints and mount filesystems
umount $(cat $PAP)"p1"
umount $(cat $PAP)"p3"
wait
rmdir mnt-p1
rmdir mnt-p3
}




#=============================

case "$1" in
	-create_img)
		create_img
		;;
	-create_par)
		create_par
		;;
	-mount_par)
		mount_par
		;;
	-format_par)
		format_par
		;;
	-umount_par)
		umount_par
		;;
	-mount_points)
		mount_points
		;;
	-umount_points)
		umount_points
		;;
	*)
		echo ""
		echo "usage: `basename $0` -options"
		echo ""
		echo "-create_img:	create an 2 GB image for the stick"
		echo "-create_par:	create the partition tables"
		echo "-mount_par:	mount partions" 
		echo "-format_par:	format the image with filesystems"
		echo "-umount_par:	umount partitions"
		echo "-mount_points:	mount filesystems"
		echo "-umount_points:	umount filesystems"
		echo ""
esac

#=============================


