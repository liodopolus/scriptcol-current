#!/bin/bash
# written by Jeffrey Scherling Mon Oct 10 21:24:53 CEST 2011
# my slackpkgbuilder with slackbuild and source in CWD

CWD=$(pwd)

# slackbuildname
SLB=$1
# basename
BAS=$(echo $SLB | cut -d . -f 1) 
# sourcename
SRC=$(ls $BAS-*.tar.*)
# target
TAR=$(echo $SRC | rev | cut -d . -f 3- | rev)

tar xvf $SLB
mv $SRC $BAS
cd $BAS
sh "$BAS.SlackBuild"

mv /tmp/$BAS-*.t?z $CWD 

#-
