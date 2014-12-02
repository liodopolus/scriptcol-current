#!/bin/sh
for a in `ls *.mp3`; do mpg321 $a ; done
