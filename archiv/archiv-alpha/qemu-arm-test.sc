#!/bin/sh
# written by Jeffrey Scherling Sun Feb 27 15:40:19 CET 2011
# test arm-architecture with qemu

USR=$(whoami)

if [ $USR = "root" ] ; then
	echo -e  "\n Don't run as Root!\n"
	exit 1
fi

# getting test-system from qemu
if [ ! -e "arm-test-0.2.tar.gz" ] ; then
wget http://wiki.qemu.org/download/arm-test-0.2.tar.gz
fi

#  extract it
if [ ! -d "arm-test"  ] ; then
tar xzvf arm-test-0.2.tar.gz
fi

# cd into text-system
cd arm-test
 
# test arm-architecture, run external kernel with initrd 

# first option with window
OPTA="-kernel zImage.integrator -initrd arm_root.img"

# secon option without window (console)
OPTB="-nographic -kernel zImage.integrator -initrd arm_root.img -nographic -append 'console=ttyAMA0'"

# testing
qemu-system-arm $OPTA
#qemu-system-arm $OPTB

# - 
