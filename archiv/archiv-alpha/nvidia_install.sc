#!/bin/sh
#Nvidia Geforce 7900 GT Installer
#sh ./NVIDIA-Linux-x86_64-185.18.36-pkg2.run --accept-license --run-nvidia-xconfig --no-network --expert --no-opengl-headers

#sh ./NVIDIA-Linux-x86_64-169.12-pkg2.run --accept-license --run-nvidia-xconfig --no-network --expert --no-opengl-headers --update -f

# stable
#sh ./NVIDIA-Linux-x86_64-190.53-pkg2.run --accept-license --run-nvidia-xconfig --no-network --expert --no-opengl-headers --kernel-source-path="/usr/src/linux/"

# testing
#sh ./NVIDIA-Linux-x86_64-195.36.08-pkg2.run --accept-license --run-nvidia-xconfig --no-network --expert --no-opengl-headers --kernel-source-path="/usr/src/linux/"

sh ./NVIDIA-Linux-x86_64-195.36.08-pkg2.run --accept-license --run-nvidia-xconfig --no-network --expert --no-opengl-headers
