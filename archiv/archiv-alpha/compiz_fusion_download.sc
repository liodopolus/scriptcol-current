#!/bin/sh
# $Id: rsync_current.sh,v 1.5 2008/04/07 22:15:00 root Exp root $
# -----------------------------------------------------------------------------
# Use rsync to mirror a Compiz-Fusion directory tree.
# Also, all the parameters that you pass this script will be appended to the
# rsync command line, so if you want to do a 'dry-run', i.e. want to look at
# what the rsync would do without actually downloading/deleting anything, add
# the '-n' parameter to the script like this:
#
#   rsync_current.sh -n
#
# -----------------------------------------------------------------------------
# Author: jsc <jsc at > :: 07apr2008
# -----------------------------------------------------------------------------
#
#Alternative Sources
#
#http://releases.compiz-fusion.org/0.7.4/
#
CWD=`pwd`
SRCNAM=compiz-fusion
VERSION=${VERSION:-0.7.4}
TOPDIR=$CWD
RSYNCURL="releases.compiz-fusion.org" 

echo ""
echo "Current Working Directory is $CWD"
echo ""
echo "Syncing version '$VERSION' ..."
echo ""

if [ ! -d ${TOPDIR}/$SRCNAM-$VERSION ]; then
  echo "Target directory ${TOPDIR}/$SRCNAM-$VERSION does not exist!"
  exit 1
fi

cd ${TOPDIR}/$SRCNAM-$VERSION
rsync $1 -avz --delete ${RSYNCURL}/$VERSION .

# ---
