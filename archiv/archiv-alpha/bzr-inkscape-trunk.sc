#!/bin/sh
# get the latest inkscape branch with bzr from (Bazaar Tree)

CWD=$(pwd)

bzr branch lp:inkscape

tar -cvzf inkscape-`date +%d-%m-%G`.tar.gz inkscape

rm -r $CWD/inkscape

echo -e "\n Inkscape Download finished! \n"

# -
