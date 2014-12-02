#!/bin/bash
# wget aikidokyritz.de httpdocs

FTP="aikidokyritz" # from where the shit comes
USER="u776901"
PASSWD="8ef3d683"
CWD=`pwd`
DATE="`date +%Y-%m-%d_%H-%M`" # date
EXT="tar.gz" # for zipping
NAME="$DATE-$FTP.$EXT" # name of backup file

wget -cm ftp://$USER:$PASSWD@ftp.$FTP.de/httpdocs/
wait

cd $CWD
mv "ftp.$FTP.de" "$FTP" 
tar -cvzf $NAME $FTP

echo -e "\n Backup Finished see $NAME \n"



