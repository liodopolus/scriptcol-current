#!/bin/sh
# written by Jeffrey Scherling Sun Jul 15 16:54:03 CEST 2012
# upgrade slackware with slackpkg, before upgrade check mirrors and blacklist

CWD=$(pwd)

update() {
	# upgrade gpg-key
	slackpkg update gpg

	# upgrade package-list
	slackpkg update
}

inst_new() {
	# install new packages
	slackpkg install-new
	echo -e "\n <slackware-install-new>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

upgr_all() {
	# upgrade packages
	slackpkg upgrade-all
	echo -e "\n <slackware-upgrade-all>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

clean_sys() { 
	# remove unofficial packages 
	slackpkg clean-system
	echo -e "\n <slackware-clean-system>: \t\t [\033[1;35m  ok  \033[0m]\n"
}

dot_new() {
	find /etc -name "*.new" > LOOK_DOT_NEW_FILES
}

boot_upg() {
	if [ -f /etc/lilo.conf ] ; then
		/sbin/lilo
		wait
		sleep 5s
	fi
}

timestamp() {
	cd $CWD
	# timestamp
	DATE="`date +%Y-%b-%d`"
	STMP="`basename $0 | cut -d - -f -3`"_"$DATE"
	rm -f "`basename $0`"*_*
	touch $STMP
}

case "$1" in
	update)
		update
		;;
	inst_new)
		inst_new
		;;
	upgr_all)
		upgr_all
		;;
	clean_sys)
		clean_sys
		;;
	sync)
		update
		inst_new
		upgr_all
		clean_sys
		dot_new
		boot_upg
		timestamp
		;;
	*)
		echo "usage: `basename $0` { update | inst_new | upgr_all | clean_sys | sync }"
esac

