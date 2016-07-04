#!/bin/sh
# written by Jeffrey Scherling Sun Sep 27 21:39:06 CEST 2015
# Nvidia Geforce GT 540M Installer


VER=NVIDIA-Linux-x86_64-352.41.run

chmod +x $VER

sh ./$VER --accept-license --run-nvidia-xconfig --no-network --expert -kernel-source-path="/usr/src/linux/" --compat32-libdir="/usr/lib/"

chmod -x $VER

