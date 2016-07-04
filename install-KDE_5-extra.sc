#!/bin/bash
# written by Jeffrey Scherling
# Sat Jan  2 17:59:01 CET 2016
# install K5 extra packages with slackpkg


EXT="
bluedevil
libktorrent
amarok
calligra
k3b
kaudiocreator
kplayer
kwebkitpart
kdevplatform
kdevelop-pg-qt
kdevelop
kdev-python
kdevelop-php
kdevelop-php-docs
kio-mtp
ktorrent
oxygen-gtk2
partitionmanager
skanlite
"

for i in $EXT ; do
	slackpkg install $i 
done

echo ""
echo "   K5 EXTRA INSTALLED"
echo ""

