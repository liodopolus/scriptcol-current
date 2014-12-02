#!/bin/sh

# Slackware build script for akonadi

# Copyright 2007-2008 Robby Workman, Northport, Alabama, USA
# All rights reserved.

# Redistribution and use of this script, with or without modification, is
# permitted provided that the following conditions are met:
#
# 1. Redistributions of this script must retain the above copyright
#    notice, this list of conditions and the following disclaimer.
#
# THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
# WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
# EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
# OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
# OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
# ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

PKGNAME=automoc4
VERSION=kdesupport_803661
ARCH=${ARCH:-x86_64}
BUILD=${BUILD:-1-js}

CWD=$(pwd)
TMP=${TMP:-/tmp}
PKG=$TMP/package-$PKGNAME

if [ "$ARCH" = "i486" ]; then
  SLKCFLAGS="-O2 -march=i486 -mtune=i686"
elif [ "$ARCH" = "s390" ]; then
  SLKCFLAGS="-O2"
elif [ "$ARCH" = "x86_64" ]; then
  SLKCFLAGS="-O2 -fPIC"
fi

rm -rf $PKG
mkdir -p $TMP $PKG
cd $TMP
rm -rf $PKGNAME-$VERSION
tar xvf $CWD/$PKGNAME-$VERSION.tar.bz2
cd $PKGNAME-$VERSION || exit 1
chown -R root:root .
find . \
 \( -perm 777 -o -perm 775 -o -perm 711 -o -perm 555 -o -perm 511 \) \
 -exec chmod 755 {} \; -o \
 \( -perm 666 -o -perm 664 -o -perm 600 -o -perm 444 -o -perm 440 -o -perm 400 \) \
 -exec chmod 644 {} \;

mkdir -p build
cd build
  KDEDIR=`kde4-config --prefix` \
  # QTDIR=/usr/lib64/qt4 \
  QTDIR=`kde4-config --qt-prefix` \
  PATH=$QTDIR/bin:$KDEDIR/bin:$PATH \
  cmake \
    -DCMAKE_C_FLAGS:STRING="$SLKCFLAGS" \
    -DCMAKE_CXX_FLAGS:STRING="$SLKCFLAGS" \
    -DCMAKE_INSTALL_PREFIX=$KDEDIR \
    -DLIB_DESTINATION=$KDEDIR/lib \
    ..
   make -j6
   make install DESTDIR=$PKG
cd -

( cd $PKG
  find . | xargs file | grep "executable" | grep ELF | cut -f 1 -d : | xargs strip --strip-unneeded 2> /dev/null || true
  find . | xargs file | grep "shared object" | grep ELF | cut -f 1 -d : | xargs strip --strip-unneeded 2> /dev/null
)

mkdir -p $PKG/usr/doc/$PKGNAME-$VERSION
cp -a AUTHORS COPYING* ChangeLog INSTALL README TODO \
  $PKG/usr/doc/$PKGNAME-$VERSION

mkdir -p $PKG/install
cat $CWD/slack-desc > $PKG/install/slack-desc

# movig pkgconfig to right place
# mv $PKG/usr/lib/pkgconfig $PKG/usr/lib64/pkgconfig
# rm -r $PKG/usr/lib

cd $PKG
/sbin/makepkg -l y -c n /tmp/$PKGNAME-$VERSION-$ARCH-$BUILD.tgz
