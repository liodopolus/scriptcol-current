#!/bin/sh
#---js---#
#virtualbox installer helper script
#usage script + file
FILE=$1
sh ./$FILE install
# license_accepted_unconditionally # add without confirmation see script + --help

# change groups for vboxusers
#usermod -G users,floppy,audio,video,cdrom,plugdev,power,netdev,scanner,vboxusers <Username>

#---js---#
