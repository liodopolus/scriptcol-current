#!/bin/bash
# written by Jeffrey Scherling
# install KDE_5

cd 5

# depencies
upgradepkg --reinstall --install-new x86_64/deps/*.t?z

# telepathy 
upgradepkg --reinstall --install-new x86_64/deps/telepathy/*.t?z

# kde 5
upgradepkg --reinstall --install-new x86_64/kde/*/*.t?z

# upgradepkg x86_64/kdei/*.t?z
upgradepkg --reinstall --install-new x86_64/kdei/kde-l10n-de-*.t?z

cd -

echo ""
echo "	KDE_5 INSTALL FINISHED"
echo ""

echo ""
find /etc/ -name "*.new" > FOUND.NEW.FILES.new
echo ""

echo ""
echo "	NOW REBOOT"
echo ""

