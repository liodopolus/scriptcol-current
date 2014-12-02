#!/bin/sh
# written by Jeffrey Scherling Tue Mar  9 01:48:38 CET 2010
# build and install nvidia-driver and nvidia-kernel package

CWD=$(pwd)
TMP=${TMP:-/tmp}


for i in nvidia-driver nvidia-kernel ; do
	cd $TMP
	tar -xvf $CWD/$i.tar.gz
	cd $i
	sh $i.SlackBuild
	cd $TMP
	rm -rf $i
done

echo -e "\n Nvidia Packages Build: \t [\033[1;35m  OK  \033[0m]\n"

for i in nvidia-driver nvidia-kernel ; do
	cd $TMP
	upgradepkg --install-new --reinstall $i*.txz
done


echo -e "\n Nvidia Packages Install: \t [\033[1;35m  OK  \033[0m]\n"

# -
