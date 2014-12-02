#!/bin/sh
# written by Jeffrey Scherling Wed Dec 26 19:31:50 CET 2012
# set environment of android-sdk-linux downloaded from android to /usr/local and cp profile scripts to etc
# script must be located in android-sdk-linux dir

# android sdk link
# https://developer.android.com/sdk/index.html
# wget -c http://dl.google.com/android/android-sdk_r21.0.1-linux.tgz


CWD=$(pwd)

ln -sf $CWD/android-sdk-linux /usr/local/android-sdk-linux

chown -R root:root /usr/local/android-sdk-linux/
chmod -R a+r /usr/local/android-sdk-linux/
chmod a+x /usr/local/android-sdk-linux/
chmod a+x /usr/local/android-sdk-linux/*/ 


cp profile.d/android-sdk.sh /etc/profile.d/
cp profile.d/android-sdk.csh /etc/profile.d/

chmod +x /etc/profile.d/android-sdk.*

source /etc/profile
echo "Environment sucessfully set"

echo "Testing Android Bridge"
adb start-server 

