#!/bin/sh
# written by Jeffrey Scherling
# get all mainboard infos

CWD=$(pwd)
FIL="all-infos"

BIO="bios-vendor bios-version bios-release-date"
SYS="system-manufacturer system-product-name system-version system-serial-number system-uuid"
BRD="baseboard-manufacturer baseboard-product-name baseboard-version baseboard-serial-number baseboard-asset-tag"
CHA="chassis-manufacturer chassis-type chassis-version chassis-serial-number chassis-asset-tag"
CPU="processor-family processor-manufacturer processor-version processor-frequency"

bios_info() {
rm -f bios_info

echo "=============bios_info begin============="  > bios_info
for OPT in $BIO ; do
	dmidecode -s $OPT >> bios_info
done
echo "=============bios_info end===============" >> bios_info
}

sys_info() {
rm -f sys_info

echo "=============sys_info begin============="  > sys_info
for OPT in $SYS ; do
	dmidecode -s $OPT >> sys_info
done
echo "=============sys_info end===============" >> sys_info
}

board_info() {
rm -f board_info

echo "=============board_info begin============="  > board_info
for OPT in $BRD ; do
	dmidecode -s $OPT >> board_info
done
echo "=============board_info end===============" >> board_info
}

chass_info() {
rm -f chass_info

echo "=============chass_info begin============="  > chass_info
for OPT in $CHA ; do
	dmidecode -s $OPT >> chass_info
done
echo "=============chass_info end===============" >> chass_info
}

cpu_info() {
rm -f cpu_info
echo "=============cpu_info begin============="  > cpu_info
for OPT in $CPU ; do
	dmidecode -s $OPT >> cpu_info
done
echo "=============cpu_info end===============" >> cpu_info
}

all_info() {
rm -f all_info
echo "=============all_info begin============="  > all_info
bios_info;sys_info;board_info;chass_info;cpu_info
$(cat bios_info sys_info board_info chass_info cpu_info >> all_info)
echo "=============all_info end===============" >> all_info
}


case "$1" in
	bios_info)
		bios_info
		;;
	sys_info)
		sys_info
		;;
	board_info)
		board_info
		;;
	chass_info)
		chass_info
		;;
	cpu_info)
		cpu_info
		;;
	all_info)
		all_info
		;;
	*)
		echo ""
		echo "usage: $(basename $0) { all_info | bios_info | sys_info | board_info | chass_info | cpu_info }"
		echo ""
esac

