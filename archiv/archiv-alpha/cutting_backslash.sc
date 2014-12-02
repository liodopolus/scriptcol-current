#!/bin/sh
# cutting (\) backslashes from filenames
for file in ls *.wav ; do echo $file | sed s/'\\ '/""/ ; done 
