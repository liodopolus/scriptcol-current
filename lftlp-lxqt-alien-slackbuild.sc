#!/bin/bash
# written by Jeffrey Scherling
# Sun Jan  3 22:00:38 CET 2016
# lftp lxqt build of alien

VER="build"

URL="http://www.slackware.com/~alien/slackbuilds/lxqt/"

lftp -c "open $URL ; mirror -c -e $VER"

echo ""
echo "   LXQT DOWNLOADED"
echo ""
