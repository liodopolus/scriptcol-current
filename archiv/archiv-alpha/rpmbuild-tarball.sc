#!/bin/sh
#---js---#
# build rpm binary from tarball

FILE=$1
rpmbuild -tb $FILE

#---js---#
