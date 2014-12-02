#!/bin/sh
# Formatting FILE_LIST vom Slamd64 Distribution to ls -1 /var/log/packages/* > Package-LIST Output
# usable for diff to compare these Lists
CWD=`pwd`
# only for Backup
cp FILE_LIST FILE_LIST.bak

# formatting and cutting programm-names
cat FILE_LIST | cut -d "/" -f 3- > tmpfile1 
cat tmpfile1 | grep asc > tmpfile2
cat tmpfile2 | sed s/.tgz.asc/""/ > FILE_LIST

# removing tmpfiles
rm tmpfile[1-2]

echo "FILE_LIST Formatted" 

# building database of current packages
cd /var/log/packages
ls -1 * > $CWD/Package_LIST
cd $CWD

echo "building Package_LIST finished" 

# formatting Lists
cat Package_LIST | cut -d . -f -1 | sed s/-[0-9]/""/ > Package-LIST-raw
cat FILE_LIST | cut -d . -f -1 | sed s/-[0-9]/""/ > FILE_LIST-raw

echo "Package Lists ready for Diff"
