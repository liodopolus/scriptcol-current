#!/bin/bash
# written by Jeffrey Scherling Mon Oct  3 23:31:28 CEST 2011
# serach *.t?z files and upgradepkg --reinstall --install-new

CWD=$(pwd)

#find $CWD -iname "*.t?z" -exec upgradepkg --dry-run --reinstall --install-new {} \; 
find $CWD -iname "*.t?z" -exec upgradepkg --reinstall --install-new {} \; 

# -
