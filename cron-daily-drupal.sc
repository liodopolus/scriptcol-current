#!/bin/sh
# drupal cron daily script

URL_A="http://aikidokyritz-localhost.de/cron.php?cron_key=6zHakWYrxFW9Z9_U1zZYE1A_Y6FKEbOVDUenIU9Xz88"
URL_B=""

# with curl
/usr/bin/curl -s $URL_A

# with wget 
#/usr/bin/wget -O - -q -t 1 $URL_A

