#!/bin/bash
# written by Jeffrey Scherling Sat Oct  8 20:27:03 CEST 2011
# upgrade slackware64 with slackpkg, before upgrade check mirrors and blacklist

# upgrade gpg-key
slackpkg update gpg

# upgrade package-list
slackpkg update

# install new packages
slackpkg install-new

# upgrade packages
slackpkg upgrade-all

# remove unofficial packages 
slackpkg clean-system

echo -e "\n <Slackware-Upgrade>: \t\t [\033[1;35m  OK  \033[0m]\n"

# timestamp
DATE="`date +%Y-%m-%d_%H-%M`"
STMP="slackware-upgrade-$DATE"
rm -f slackware-upgrade-20*
touch $STMP

# -
