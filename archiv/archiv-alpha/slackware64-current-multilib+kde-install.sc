#!/bin/bash
# written by Jeffrey Scherling Sun Sep 18 19:19:52 CEST 2011
# install multilib packages and kde packages from alien
# for specials like ! removepkg ! see # KDE/# Specials at bottom

CWD=$(pwd)


MULVER="current"
KDEVER="4.7.1"


# dry=dry-run

MUL="no"
KDE="yes" 


# Multilib

if [ "$MUL" = "yes" ] ; then 

	upgradepkg --reinstall --install-new $MULVER/*.t?z
	echo -e "\n Multilib-$MULVER Install : \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$MUL" = "dry" ] ; then 

	upgradepkg --dry-run --reinstall --install-new $MULVER/*.t?z
	echo -e "\n Multilib-$MULVER Dry-Run : \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$MUL" = "no" ] ; then 
	
	echo -e "\n\t\t [\033[1;35m  Multilib not changed  \033[0m]\n"

else

	echo -e "\n\t\t [\033[1;35m  ERROR \033[0m]\n"
	exit 1

fi


# KDE

if [ "$KDE" = "yes" ] ; then 

	upgradepkg --reinstall --install-new $KDEVER/x86_64/deps/*.t?z
	echo -e "\n KDE-$KDEVER-Depencies Install : \t\t [\033[1;35m  OK  \033[0m]\n"
	upgradepkg --reinstall --install-new $KDEVER/x86_64/kde/*.t?z
	echo -e "\n KDE-$KDEVER-Packages Install : \t\t [\033[1;35m  OK  \033[0m]\n"
# Specials for kde 4.5.5 to kde 4.7.1---
	removepkg polkit-kde-1
	removepkg kdebase-runtime
	removepkg kdebase-workspace
	removepkg kdebindings
	removepkg kdeedu
	removepkg konq-plugins
# --------------------------------------
	echo -e "\n KDE-$KDEVER-Removepkg : \t\t [\033[1;35m  OK  \033[0m]\n"
	for lang in de en_GB hu it pl ru ; do 
  		upgradepkg --reinstall --install-new $KDEVER/x86_64/kdei/kde-l10n-$lang-*.t?z
	done
	echo -e "\n KDE-$KDEVER-Languages Install : \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n KDE-$KDEVER-Desktop Install : \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n\t\t [\033[1;35m  Check .new config Files !  \033[0m]\n"
	find /etc/ -name "*.new"
	echo -e "\n [\033[1;35m  Reboot in KDE-$KDEVER \033[0m]\n"

elif [ "$KDE" = "dry" ] ; then 

	upgradepkg --dry-run --reinstall --install-new $KDEVER/x86_64/deps/*.t?z
	echo -e "\n KDE-$KDEVER-Depencies DryRun : \t\t [\033[1;35m  OK  \033[0m]\n"
	upgradepkg --dry-run --reinstall --install-new $KDEVER/x86_64/kde/*.t?z
	echo -e "\n KDE-$KDEVER-Packages DryRun : \t\t [\033[1;35m  OK  \033[0m]\n"
	# specials for kde 4.5.5 to kde 4.7.1
	removepkg -warn polkit-kde-1
	removepkg -warn kdebase-runtime
	removepkg -warn kdebase-workspace
	removepkg -warn kdebindings
	removepkg -warn kdeedu
	removepkg -warn konq-plugins
	echo -e "\n KDE-$KDEVER-Removepkg DryRun : \t\t [\033[1;35m  OK  \033[0m]\n"
	for lang in de en_GB hu it pl ru ; do 
  		upgradepkg --dry-run --reinstall --install-new $KDEVER/x86_64/kdei/kde-l10n-$lang-*.t?z
	done
	echo -e "\n KDE-$KDEVER-Languages DryRun : \t\t [\033[1;35m  OK  \033[0m]\n"
	echo -e "\n KDE-$KDEVER-Desktop DryRun: \t\t [\033[1;35m  OK  \033[0m]\n"

elif [ "$KDE" = "no" ] ; then 

	echo -e "\n\t\t [\033[1;35m  KDE not changed  \033[0m]\n"

else

	echo -e "\n\t\t [\033[1;35m  ERROR \033[0m]\n"
	exit 1

fi


# -
