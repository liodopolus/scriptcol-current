#!/bin/sh
# written by Jeffrey Scherling Fri Jul 30 00:01:56 CEST 2010
# rsync slackware-current tree, create compat32-packages and install them, cleaning /tmp

CWD=$(pwd)
TMP=${TMP:-/tmp}
DISTRIB="slackware"
VERSION=${VERSION:-current}
TOPDIR="$CWD/compat32-upgrade"

RSYNCURL="rsync.slackware.pl::slackware"

echo ""
echo "Syncing version <'$DISTRIB-$VERSION'> ..."
echo ""

if [ ! -d ${TOPDIR}/$DISTRIB-$VERSION ]; then
  echo "Target directory ${TOPDIR}/$DISTRIB-$VERSION does not exist!"
  echo ""
  exit 1
fi

cd ${TOPDIR}/$DISTRIB-$VERSION

# change (ex)clude to (in)clude, see massconvert.sh
rsync $1 -avz --delete \
  --include "a" \
  --include "ap" \
  --include "d" \
  --exclude "e" \
  --exclude "f" \
  --exclude "k" \
  --exclude "kde" \
  --exclude "kdei" \
  --include "l" \
  --include "n" \
  --exclude "t" \
  --exclude "tcl" \
  --include "x" \
  --exclude "xap" \
  --exclude "y" \
  --delete-excluded ${RSYNCURL}/$DISTRIB-$VERSION/$DISTRIB/ .

echo -e "\n Syncing <slackware-current>: \t\t [\033[1;35m  OK  \033[0m]\n"

# timestamp
DATE="`date +%Y-%m-%d_%H-%M`"
STMP="Last-Tree-Update-$DATE"
touch $STMP


# convert and install compat32 packages
cd ${TOPDIR}

CONVERT="yes"
INSTALL="yes"

SRC="${TOPDIR}/$DISTRIB-$VERSION"
TAR="${TOPDIR}/compat32-packages"


if [ ! -d $SRC ] ; then
       	echo -e "\n Directory: [\033[1;35m $SRC \033[0m] not exist! \n"
       	exit 1
fi

if [ "$CONVERT" = "yes" ] ; then 
	( massconvert32.sh -i $SRC -d $TAR )
	echo -e "\n Conversion: \t\t [\033[1;35m  OK  \033[0m]\n"
else
	echo -e "\n No Conversion \n"
fi

if [ "$INSTALL" = "yes" ] ; then 
	cd $TAR 
	for dir in  a-compat32 ap-compat32 d-compat32 l-compat32 n-compat32 x-compat32 ; do
	       	( cd $dir ; upgradepkg --install-new *.t?z )
	done 
else 
	echo -e "\n No Installation \n" 
fi


# timestamp
cd $TAR
DATE="`date +%Y-%m-%d_%H-%M`"
STMP="Last-compat32-upgrade-$DATE"
rm -f Last-compat32-upgrade-*
touch $STMP

# cleaning /tmp
PKGS="$TMP/package-*"
rm -rf $PKGS

echo -e "\n Installation of <compat32-packages> : \t\t [\033[1;35m  OK  \033[0m]\n"


# -
