#!/bin/bash
# written by Jeffrey Scherling
# find newest packages

# find packages from today only in the fist level of directories
#find .  -maxdepth 2 -type f -name "*.t?z" -mtime 0 -print0 -execdir ls -al '{}' \+

# find packages from today and reinstall them
#find .  -maxdepth 2 -type f -name "*.t?z" -mtime 0 -print0 -execdir upgradepkg --reinstall '{}' \+


