#!/bin/sh
#svn cleanup directories
for DIR in KDE kdesupport webkam-0.2 ; do ( echo "cleaning > $DIR <" ; svn cleanup $DIR ) ; done


