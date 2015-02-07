#!/bin/bash
# sdocs-builder
# build the sphinx docs


# functions for building

once() {
	touch sdocs/overview/overview.rst
	make html ; make html
}

snapshot() {
for ((i=1 ; i<=50 ; i++ )); do 
	touch sdocs/overview/overview.rst
	make html ; sleep 6 
done
}

repeat() {
while true ; do
	touch sdocs/overview/overview.rst
	make html ; sleep 6
done
}


case "$1" in
	once)
		once
		;;
	snapshot)
		snapshot
		;;
	repeat)
		repeat
		;;
	*)	
		echo "usage: `basename $0` { once | snapshot ( 5 min ) | repeat ( every 6s ) }"
esac

