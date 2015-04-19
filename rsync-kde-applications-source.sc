#!/bin/sh
# written by Jeffrey Scherling
# rsync script for kde applications 14.12.2

CWD=$(pwd)

DIS="kde"
#VER=${VER:-current}
VER=${VER:-14.12.2}

HOS=$(hostname)
TAR=/media/"$HOS"_system/source/$DIS/applications/$VER

# without backslash at end
#RSYNCURL="rsync://ftp5.gwdg.de/pub/linux/kde/stable/$VER/src"
RSYNCURL="rsync://ftp5.gwdg.de/pub/linux/kde/stable/applications/$VER/src"
# official site http://download.kde.org/stable/applications/$VER/src


if [ ! -d $TAR ]; then
	echo "Creating Target Directory $TAR !"
	mkdir -p $TAR
	echo ""
fi


cd $TAR


pull() {
	echo "pull $DIS-$VER to $TAR"
	#	rsync -avz --delete --progress $RSYNCURL/ .

        rsync \
	-avz \
	--delete \
	--delete-excluded \
	--progress \
	--include "kde-l10n/kde-l10n-de*" \
	--exclude "kde-l10n/*.tar.xz" \
	--links \
	--copy-links \
	$RSYNCURL/ .

	echo -e "\n <pull $DIS-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dry() {
	echo "dry $DIS-$VER to $TAR" 
	#	rsync -n -avz --delete --progress $RSYNCURL/ .

	rsync -n \
	-avz \
	--delete \
	--delete-excluded \
	--progress \
	--include "kde-l10n/kde-l10n-de*" \
	--exclude "kde-l10n/*.tar.xz" \
	--links \
	--copy-links \
	$RSYNCURL/ .

	echo -e "\n <dry $DIS-$VER>: \t\t [\033[1;35m  ok  \033[0m]\n"
}


case "$1" in
  pull)
     pull
    ;;
  dry)
    dry
    ;;
  *)
  echo -e "\n usage: `basename $0` { pull | dry }\n"
esac

