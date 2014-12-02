#!/bin/bash
# written by Jeffrey Scherling Thu Oct 14 21:05:16 CEST 2010
# get multilib packages and kde packages from alien 

LIB="yes"
KDE="yes"


VERMULTILIB="current" # slackware-current
#URLMULTILIB="http://connie.slackware.com/~alien/multilib/"
RSYNCURLMULTILIB="rsync://taper.alienbase.nl/mirrors/people/alien/multilib/$VERMULTILIB"

VERKDE="4.7.1"

# LFTP - Mirrors
# Standard
URLKDE="http://alien.slackbook.org/ktown/$VERKDE/" 
# Special
#URLKDE="http://alien.slackbook.org/kdesc/$VERKDE/" 

# RSYNC - Mirrors
# Standard
#RSYNCURLKDE="rsync://alien.slackbook.org/alien-kde/$VERKDE"
# US-Mirror
RSYNCURLKDE="rsync://taper.alienbase.nl/mirrors/alien-kde/$VERKDE"


# libary updates are almost in the same directory
if [ "$LIB" = "yes" ] ; then 
	#lftp -c "open $URLMULTILIB ; mirror $VERMULTILIB"
	rsync -avz --delete --progress --exclude "debug" --exclude "slackware64-compat32" $RSYNCURLMULTILIB .
	echo -e "\n Upgrade <multilib-$VERMULTILIB>: \t\t [\033[1;35m  OK  \033[0m]\n"
else
	echo -e "\n No Multilib \n"
fi


if [ "$KDE" = "yes" ] ; then 
	#lftp -c "open $URLKDE ; mirror x86_64"
	rsync -avz --delete --progress --exclude "source" --exclude "x86" $RSYNCURLKDE .
	echo -e "\n Upgrade <kde-$VERKDE>: \t\t [\033[1;35m  OK  \033[0m]\n"
else
	echo -e "\n No KDE \n"
fi


# -
