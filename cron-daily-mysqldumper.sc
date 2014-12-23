#!/bin/sh
# mysqldumper cron daily 

perl /var/www/nginx/mysqldumper/msd_cron/crondump.pl -config=mysqldumper -html_output=0 

