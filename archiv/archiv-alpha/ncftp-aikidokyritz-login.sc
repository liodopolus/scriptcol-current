#!/bin/sh
# 
USER="u776901"
PASSWD="8ef3d683"
URL="ftp.aikidokyritz.de"
PORT="21"
ncftp -u $USER -p $PASSWD -P $PORT $URL
