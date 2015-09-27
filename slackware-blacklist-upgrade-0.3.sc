#!/bin/sh
# written by Jeffrey Scherling Sun Sep 27 14:42:02 CEST 2015
# setup slackpkg blacklist to meet my needs ;-)
#
# 0.2 changed blacklisted packages 
# 0.3 changed blacklisted packages and updated date

DATE=`date`

echo ""
echo "CHANGING BLACKLIST (yes/no):" ; read ANS

if [ "$ANS" = "yes" ] ; then 

SRC="/etc/slackpkg/blacklist"


# new blacklist file, don't change unless you know what you are doing!
cat << EOF > $SRC
# generated by Jeffrey Scherling $DATE
#---8<---

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
kernel-firmware
kernel-generic
kernel-generic-smp
kernel-headers
kernel-huge
kernel-huge-smp
kernel-modules
kernel-modules-smp
kernel-source

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
# Trusted Builds
#
# Slackbuilds
[0-9]+_SBo
# Alien
# now managed bei slackpkg+
#[0-9]+alien
# Compat32
# now managed bei slackpkg+
#[0-9]+compat32
# Jeffs
[0-9]+js
# Ponce
[0-9]+ponce
# Sbopkg
[0-9]+_cng

# Untrusted Builds
#libkgapi
#kdepimlibs
#kdepim-runtime
#kdepim

# Gnomeslackbuilds
[0-9]+gsb
xfce

# Specials
minidlna
calibre1
# because of system damage
slackpkg+

# Sound Problem April 2015
phonon-gstreamer

# only [de] packages look ,
kde-l10n-[a-c,e-z][a-d,f-z]
calligra-l10n-[a-c,e-z][a-d,f-z]
calligra-l10n-en_GB

# difficult to select
kde-l10n-da
kde-l10n-en_GB
kde-l10n-he
kde-l10n-nds
kde-l10n-pt_BR
kde-l10n-zh_CN
kde-l10n-zh_TW

#---8<---
EOF


elif [ "$ANS" = "no" ] ; then 
	echo ""
	echo "NOTHING CHANGED"
	echo ""
	exit 1
else
	echo ""
	echo "NOTHING CHANGED"
	echo ""
	exit 1
fi

echo -e "\n SETUP BLACKLIST: \t\t [\033[1;35m  OK  \033[0m]\n"

