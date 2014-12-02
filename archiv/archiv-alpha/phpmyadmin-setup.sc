#!/bin/sh
# written by Jeffrey Scherling Thu May 13 17:52:44 CEST 2010
# first do step 1 yes, then configure, then do step 2 yes and all is ready

STEP1="no"
STEP2="yes"

if [ "$STEP1" = "yes" ] ; then
	cd myadmin
	mkdir config
	chmod o+rw config
	echo "---------------"
	echo "Step-1 Finished"
	echo "---------------"
fi

if [ "$STEP2" = "yes" ] ; then
	cd myadmin
	mv config/config.inc.php .
	chmod o-rw config.inc.php
	rm -rf config
	echo "---------------"
	echo "Step-2 Finished"
	echo "---------------"
fi

# -
