#!/bin/sh
# create DSA ssh key for ssh-logins without authentication
# usage 

KEYNAME="testkey" # create key in home 
HOME=~/.ssh

ssh-keygen -t dsa -b 1024 -f $HOME/$KEYNAME # rsa is default with 2048 bits
 
echo -e "\n Check $HOME for [ $KEYNAME ] \n"


