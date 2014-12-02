#!/bin/sh
# Building Grub Boot Iso of current Grub Configuration

# directories
CDIR=`pwd`
IDIR="$CDIR/iso"
BDIR="$CDIR/iso/boot"
GDIR="$CDIR/iso/boot/grub"
LGDIR="/boot/grub"

# files
KERNEL=`grep kernel /boot/grub/menu.lst | cut --delimiter=" " --fields=4`
EL_STAGE2="/`grep stage2_eltorito  /var/log/packages/grub*`"

echo "--- Building Grub Boot Iso ---"

# copy files
mkdir -p $GDIR
cp $EL_STAGE2 $GDIR
cp $KERNEL $BDIR
cp $LGDIR/menu.lst $GDIR

# make iso
mkisofs -R -b boot/grub/stage2_eltorito -no-emul-boot -boot-load-size 4 -boot-info-table -o grub.iso $IDIR
rm -r $IDIR

echo "--- Grub Boot Iso Finished ---"

