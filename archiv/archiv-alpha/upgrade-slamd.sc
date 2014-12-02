#!/bin/sh
#upgrade slamd 64
SRCDIR="/root/slackware"
echo $SRCDIR
# 1. 
upgradepkg $SRCDIR/a/glibc-solibs-*.tgz
wait

echo "glibc-solibs finished"
echo "glibc-solibs finished"
echo "glibc-solibs finished"

# 2.
upgradepkg $SRCDIR/a/pkgtools-*.tgz
wait

echo "pkgtools finished"
echo "pkgtools finished"
echo "pkgtools finished"

# 3. everything ?
#upgradepkg --install-new /root/slackware/*/*.tgz

# 4. upgrading special packages
cd $SRCDIR
    for dir in a ap d e f k kde l n t tcl x xap y ; do
      ( cd $dir ; upgradepkg *.tgz )
    done
wait

# You may also wish to go into /var/log/packages and take a look at the package list:
#    ls -lt | less
# 5. 
     cd /etc
     find . -name "*.new" | while read configfile ; do
       if [ ! "$configfile" = "./rc.d/rc.inet1.conf.new" \
         -a ! "$configfile" = "./rc.d/rc.local.new" \
         -a ! "$configfile" = "./group.new" \
         -a ! "$configfile" = "./passwd.new" \
         -a ! "$configfile" = "./shadow.new" ]; then
         cp -a $(echo $configfile | rev | cut -f 2- -d . | rev) \
           $(echo $configfile | rev | cut -f 2- -d . | rev).bak 2> /dev/null
         mv $configfile $(echo $configfile | rev | cut -f 2- -d . | rev)
       fi
     done
wait

# 6. kde - languages 
cd $SRCDIR/kdei
upgradepkg  k*-de-*.tgz

cd /root

echo "all up to date"
echo "all up to date"
echo "all up to date"

# 7. telinit 3
#telinit 3
