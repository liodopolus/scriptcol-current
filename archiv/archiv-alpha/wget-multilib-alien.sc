#!/bin/sh
# written by Jeffrey Scherling Sun Mar  7 18:42:21 CET 2010
# download aliens multilib package

VERSION="13.1"
wget -drc -o log -- -x http://connie.slackware.com/~alien/multilib/$VERSION/
rm log

echo -e "\n Download: \t\t [\033[1;35m  OK  \033[0m]\n"

# -
