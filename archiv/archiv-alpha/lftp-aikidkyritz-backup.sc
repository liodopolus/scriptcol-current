#!/bin/sh
# written by Jeffrey Scherling Tue Mar  9 03:15:58 CET 2010
# get aikidokyritz.de httpdocs with lftp

# basic settings from where to grab
DOM="aikidokyritz" # domain
URL="ftp.$DOM.de"
USER="u776901"
PASSWD="8ef3d683"
PORT="21"

# specify content of mirror here, only one directory is supported
SRC="httpdocs" 

# settings for zipping
CWD=`pwd`
DATE="`date +%Y-%m-%d_%H-%M`" # date
EXT="tar.gz" # for zipping
NAME="$DATE-$DOM-jsb.$EXT" # name of backup file


# list content of url 
echo -e "\n Content of [ $URL/$SRC ] is ... \n"
echo -e "---------------------------------------------------------------"

lftp -c "open -e ll -u $USER,$PASSWD -p $PORT $URL/$SRC" # if doesn't work, then set <alias ll ls -al> in etc/lftp.conf

echo -e "\n Listing Content [ $URL/$SRC ] finished"
echo -e "---------------------------------------------------------------"


# mirror content of url
echo -e "\n Mirror [ $SRC ] from [ $URL ] ... \n"
echo -e "---------------------------------------------------------------"

lftp -c "open -u $USER,$PASSWD -p $PORT $URL ; mirror --continue --parallel=5 $SRC $DOM/ " # set ftp:list-options -a in etc/lftp.conf to get .files

echo -e "\n Mirror of $SRC finished"
echo -e "---------------------------------------------------------------"


# check for backup directory and zipping
echo -e "\n Zipping [ $SRC ] in [ $DOM ] \n"
echo -e "---------------------------------------------------------------"

if [ ! -d ${DOM} ]; then
	echo -e "\n ERROR: Backup directory ${DOM} does not exist! \n"
	exit 1
fi

tar -cvzf $NAME $DOM

echo -e "\n Zipping: [ $NAME ] finished"
echo -e "---------------------------------------------------------------"

# -
