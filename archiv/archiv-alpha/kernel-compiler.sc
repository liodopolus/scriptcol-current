#!/bin/sh
#

KERNEL="small-2.6.33"

# compile and make modules
make clean bzImage modules modules_install
wait

# copy to boot
cp arch/x86_64/boot/bzImage /boot/$KERNEL
wait

# link to vmlinus
ln -sf /boot/$KERNEL /boot/vmlinus
wait

wait
reboot

