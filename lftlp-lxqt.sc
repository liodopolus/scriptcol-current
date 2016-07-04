#!/bin/bash
# written by Jeffrey Scherling
# Sun Jan  3 22:00:38 CET 2016
# lftp lxqt

VER="0.10.0"

URL="https://downloads.lxqt.org/lxqt/"

lftp -c "open $URL ; mirror -c -e $VER"

echo ""
echo "   LXQT DOWNLOADED"
echo ""
