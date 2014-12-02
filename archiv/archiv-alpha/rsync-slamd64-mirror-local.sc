#!/bin/sh
# Copy with rsync auf localer Festplatte von Source nach Target
#
CWD=`pwd`
INFO=${VERSION:-Data}
SOURCEDIR="/"
TARGETDIR="/mnt/slackware64"

echo ""
echo "Syncing '$INFO'"
echo ""
echo "SOURCE is $SOURCEDIR"
echo ""
echo "TARGET is $TARGETDIR"
echo ""

if [ ! -d ${TARGETDIR} ]; then
  echo "Target directory ${TARGETDIR} does not exist!"
  exit 1
fi

rsync -avz --delete --exclude "/mnt" --exclude "/tmp" --exclude "/sys" --exclude "/proc" ${SOURCEDIR} ${TARGETDIR}

cd $TARGETDIR
mkdir mnt tmp sys proc

cd $CWD

# echo output created
echo -e "\n Change $TARGETDIR/etc/fstab to you needs!\n"
echo -e "\n \033[1;35m[ System in Sync ]\033[0m \n" # violett

# ---
