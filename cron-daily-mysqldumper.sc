#!/bin/sh
# vor daily cron
#perl /var/www/nginx/mysqldumper/msd_cron/crondump.pl -config=jeffrey_mysqldumper_config -html_output=0

perl /var/www/nginx/mysqldumper/msd_cron/crondump.pl -html_output=0

