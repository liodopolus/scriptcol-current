#!/bin/sh
# written by Jeffrey Scherling Fri Dec  3 19:52:31 CET 2010
# set permissions, hardening the system


# set directory permissions hardening
find . -type d -exec chown root:users {} \;
find . -type d -exec chmod 755 {} \;


# set file permissions hardening
find . -type f -exec chown root:users {} \;
find . -type f -exec chmod 640 {} \;


# set script permissions hardening
find . -type f -iname '*.sc' -exec chown root:root {} \;
find . -type f -iname '*.sc' -exec chmod 700 {} \;


# -
