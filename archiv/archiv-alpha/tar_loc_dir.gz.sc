#!/bin/sh
# tar Directories in CWD
REV=803468
for DIR in \
kdeaccessibility \
kdeadmin \
kdeartwork \
kdebase \
kdebindings \
kdeedu \
kdegames \
kdegraphics \
kdelibs \
kdemultimedia \
kdenetwork \
kdepim \
kdepimlibs \
kdesdk \
kdetoys \
kdeutils \
kdevelop \
kdevplatform \
kdewebdev ;  do ( echo "Building:[$DIR-rev-$REV.tar.gz]" && tar czf $DIR-rev-$REV.tar.gz $DIR ) ; done

echo ""
echo "All Done"
echo ""
