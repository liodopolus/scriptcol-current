#!/bin/sh
# concate pdf files 

pdfconcat -o binder.pdf $(ls *.pdf | sort) 

