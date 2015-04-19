#!/bin/bash
# written by Jeffrey Scherling
# extract package-names from package-names-0.2.9-x86_64-1.t?z


ext() {

# extract package names
ls $2 | sed s/-[0-9].*.*//g

}


case $1 in
	ext)
		ext
		;;
	*)
		echo ""
		echo "usage: $(basename $0) [ ext /directory ]"
		echo ""
esac

