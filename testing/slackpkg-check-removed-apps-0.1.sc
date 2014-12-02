#!/bin/sh
# written by Jeffrey Scherling Fri Jul 13 21:02:28 CEST 2012
# check apps status (diff) from slackware-tree with slackpkg

CWD=$(pwd)

TAR="/tmp"
INS="$TAR/PACKAGE_INSTALLED.TXT"
UNI="$TAR/PACKAGE_UNINSTALLED.TXT"
UPG="$TAR/PACKAGE_UPGRADE.TXT"

SLA="a ap d e f k kde kdei l n t tcl x xap y"

# show installed
ins() {
echo "WAIT A MOMENT"
if [ ! -e $INS ] ; then
	for LIS in $SLA ; do 
		slackpkg search $LIS | grep installed	>> $INS
	done
fi
cat $INS 
}


# show uninstalled
uni() {
echo "WAIT A MOMENT"
if [ ! -e $UNI ] ; then
	for LIS in $SLA ; do 
		slackpkg search $LIS | grep uninstalled	>> $UNI
	done
fi
cat $UNI 
}


# show upgrade
upg() {
echo "WAIT A MOMENT"
if [ ! -e $UPG ] ; then
	for LIS in $SLA ; do 
		slackpkg search $LIS | grep upgrade	>> $UPG
	done
fi
cat $UPG
}


# cleanup
clean() {
	rm -f $INS $UNI $UPG
	echo ""
	echo "CLEANUP_FINISHED"
	echo ""
}


case $1 in
	ins)
	  ins
	;;
	uni)
	  uni
	;;
	upg)
	  upg
	;;
	clean)
	  clean
	;;
	*)
	  echo "usage: `basename $0` {ins|uni|upg|clean}"
esac

