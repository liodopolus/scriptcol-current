#!/bin/sh
# convert png to pdf and concate them

for i in *.png ; do 
	convert $i "$(basename $i .png).pdf" ; 
done

