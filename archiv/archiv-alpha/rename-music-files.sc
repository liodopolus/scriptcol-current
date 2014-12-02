#!/bin/sh
# step by step rename for music files 
# attention please check settings befor mv !

# select file by end
END="wma"

# converting selection 
FILE="*.$END"

# replaces words in names
#REG="Search"
#REP="Replacement"
#for J in $FILE ; do mv "$J" "`echo $J | sed s/"$REG"/"$REP"/`" ; done 
#rename "$REG" "$REP" $FILE

# delete specials truncate 
#SYM="a-f"
#SYM="0-6"
#SYM="[:space:]"
#for J in $FILE ; do mv "$J" "`echo $J | tr -d "$SYM"`" ; done 

# cutting things
#CUT="4-"
#for J in $FILE ; do mv "$J" "`echo $J | cut -b $CUT`" ; done 

# replaces end
REP="mp3"
#rename $END $REP $FILE

# add zero in front of file
# select files by numbers
#SEL="[1-9]*"
#for J in $SEL ; do mv "$J"  "0$J" ; done 

#---




