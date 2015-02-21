#!/bin/sh
# translate python google module have to be installed first

# languages
L="pl it fr ru ja"

# words to translate
# ./translate.sc "words"

for i in $L ; do 
	translate -t $i -f de "$1"
done

