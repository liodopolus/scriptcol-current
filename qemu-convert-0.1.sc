#!/bin/sh
# written by Jeffrey Scherling Sat Sep 29 13:59:17 CEST 2012
# convert qemu images

CWD=$(pwd)

#default base image
NAM="winxp-6G-2012-Oct-03.raw"

set -e

raw_qcow2() {
	# raw to qcow2
echo -e "\n Converting Qemu Image? [yes|no]:" ; read ANS

if [ "$ANS" = "yes" ] ; then

	EXT="qcow2"

	echo ""
	echo "Converting please wait"
	echo ""
	# default
	qemu-img convert -O $EXT -S 4G $NAM "$(basename $NAM .raw).$EXT"

	# optimized cluster
	#qemu-img convert -O $EXT -o cluster_size=1024,preallocation=metadata -S 4G $NAM "$(basename $NAM .raw).$EXT"

elif [ "$ANS" = "no" ] ; then
	echo -e "\n Nothing done "
else
	exit 1
fi

}

case "$1" in
	raw_qcow2)
		raw_qcow2
		;;
	*)
		echo ""
		echo "usage: $(basename $0) { raw_qcow2 }"
		echo ""
esac

