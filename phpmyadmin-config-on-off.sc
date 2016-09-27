#!/bin/bash
# written by Jeffrey Scherling
# Sun Aug 28 20:18:15 CEST 2016

# on/off phpmyadmin config folder
# after you run /setup in you webbrowser restore security with off

TAR=/var/www/nginx/phpmyadmin

cd $TAR


on() {

#Activate

mkdir config                        # create directory for saving
chmod o+rw config                   # give it world writable permissions


cp config.sample.inc.php config/config.inc.php	# copy current configuration for editing
chmod o+w config/config.inc.php     		# give it world writable permissions

}


off() {

# Deactivate

mv config/config.inc.php .         # move file to current directory
chmod o-rw config.inc.php          # remove world read and write permissions
rm -rf config                      # remove not needed directory

}


# Commands

case $1 in

	on)
		on	# Activate
		;;
	off)
		off	# Deactivate
		;;
	*)
			# For all other cases
	echo ""
        echo "   usage: $(basename $0 .sc) CMD   "
	echo "                                   "
	echo "   CMD: DESCRIPTION                "
	echo "   on : create config folder       "
	echo "   off: delete config folder       "
	echo "                                   "
		;;
esac

