#!/bin/sh
# written by Jeffrey Scherling Fri Jul 30 01:55:20 CEST 2010
# upgrade slackpkg blacklist, with personal packages by names

# overwrite /etc/blacklist
ALL="yes"

# write separate files in cwd
#ALL="no"

# blacklist packages by names
NAMES="js alien compat32" 

DATE=`date`


if [ "$ALL" = "yes" ] ; then 

TAR="/etc/slackpkg/blacklist"
OLD="/etc/slackpkg/blacklist.old"

# backup if necessary
#cp $TAR $OLD

# new blacklist file, don't change unless you know what you are doing!
cat << EOF > $TAR
# written by Jeffrey Scherling $DATE

# ----------------------------------------------------------------------- 
# This is a blacklist file. Any packages listed here won't be
# upgraded, removed, or installed by slackpkg.
#
# The correct syntax is:
#
# To blacklist the package xorg-server-1.6.3-x86_64-1 the line will be:
# xorg-server
#
# DON'T put any space(s) before or after the package name or regexp.
# If you do this, the blacklist will NOT work.

#
# Automated upgrade of kernel packages aren't a good idea (and you need to
# run "lilo" after upgrade). If you think the same, uncomment the lines
# below 
#
#kernel-firmware
#kernel-generic
#kernel-generic-smp
#kernel-headers
#kernel-huge
#kernel-huge-smp
#kernel-modules
#kernel-modules-smp
#kernel-source

#
# aaa_elflibs can't be updated.
#
aaa_elflibs

# You can blacklist using regular expressions.
#
# Don't use *full* regex here, because all of the following 
# will be checked for the regex: series, name, version, arch, 
# build and fullname.
#
# This one will blacklist all SBo packages:
#[0-9]+_SBo
# ----------------------------------------------------------------------- 


# jeff's addons don't update or install this
emacs
polkit-gnome
seamonkey
seamonkey-solibs
xfce
xfce4-notifyd
xfce4-power-manager
windowmaker

# cups (new version 1.4.2 won't work with canon 3.0 drivers)
cups
cups-compat32
# -
EOF


for i in $NAMES ; do
	echo "" >> $TAR
	echo "# $i $DATE" >> $TAR
	ls -1 /var/log/packages/* | grep "$i" | cut -d / -f 5- | sed -e s/-[0-9].*$// >> $TAR
	echo "# -" >> $TAR
done


elif [ "$ALL" = "no" ] ; then 

for i in $NAMES ; do
	rm $i-blacklist.new
	echo "# $i $DATE" >> $i-blacklist.new
	ls -1 /var/log/packages/* | grep "$i" | cut -d / -f 5- | sed -e s/-[0-9].*$// >> $i-blacklist.new
	echo "# -" >> $i-blacklist.new
done


fi

echo -e "\n Upgrade </etc/slackpkg/blacklist>: \t\t [\033[1;35m  OK  \033[0m]\n"


# -

