#!/bin/sh
# create DSA ssh key 

KEYNAME="rsync-aikidokyritz" # create key in home 
ssh-keygen -t dsa -b 1024 -f ~/$KEYNAME
