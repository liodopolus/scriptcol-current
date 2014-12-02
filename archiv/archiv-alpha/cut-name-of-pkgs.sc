#!/bin/sh
# cut the end of packagename, extract name for slackpkg blacklist

SRC=$1
FILE="$SRC.new"

# create list and cut after -[0-9] 
cat $SRC | sed -e s/-[0-9].*$// > $FILE

# echo output created
echo -e "\n \033[1;35m[<$FILE> created]\033[0m \n" # violett


