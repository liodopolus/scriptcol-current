#!/bin/sh
# $Id: rsync_current.sh,v 1.5 2008/04/07 22:15:00 root Exp root $
# -----------------------------------------------------------------------------
# Use rsync to mirror a Slackware directory tree.
# The default is to make a mirror of slackware-current, but you can alter that
# by running the script like this:
#
#   VERSION=12.2 rsync_slamd62-12.2.sh
#
# ...which will mirror Slackware-12.2 instead.
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
#anorien.warwick.ac.uk/slamd64/
#ftp.heanet.ie/pub/slamd64/
#goudrenet.student.utwente.nl
#rsync.slackware.pl::slamd64
#
CWD=`pwd`
VERSION=${VERSION:-12.2}
TOPDIR=$CWD
#RSYNCURL="rsync.slackware.pl::slamd64"
RSYNCURL="goudrenet.student.utwente.nl::slamd64"

echo ""
echo "Current Working Directory is $CWD"
echo ""
echo "Syncing version '$VERSION' ..."
echo ""

if [ ! -d ${TOPDIR}/slamd64-$VERSION ]; then
  echo "Target directory ${TOPDIR}/slamd64-$VERSION does not exist!"
  echo ""
  exit 1
fi

cd ${TOPDIR}/slamd64-$VERSION
rsync $1 -avz --delete --exclude "pasture/*" ${RSYNCURL}/slamd64-$VERSION/slamd64/ .

# ---


