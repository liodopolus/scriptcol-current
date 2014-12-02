#!/bin/sh
# written by Jeffrey Scherling Fri Jan  4 04:31:42 CET 2013
# create ssl key and certificate for https connections

NAM="server"

openssl genrsa -des3 -out $NAM.key 1024
openssl req -new -key $NAM.key -out $NAM.csr
cp $NAM.key $NAM.key.tmp
openssl rsa -in $NAM.key.tmp -out $NAM.key
openssl x509 -req -days 365 -in $NAM.csr -signkey $NAM.key -out $NAM.crt

echo ""
echo "KEY AND CERTIFICATE GENERATED"
echo ""
