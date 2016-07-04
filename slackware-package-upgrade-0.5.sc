#!/bin/sh
# written by Jeffrey Scherling Sun Sep 27 15:05:32 CEST 2015
# upgrade slackware with slackpkg, before upgrade check mirrors and blacklist
# look at http://docs.slackware.com/howtos:slackware_admin:systemupgrade
#
# 0.2 changed slackpkg upgrade first, after that glibc-solibs
# 0.3 added initrd and lilo
# 0.4 commented initrd and lilo out from sync because of LTS + Nvidia
# 0.5 initrd uname -r added 

CWD=$(pwd)

update() {
	# upgrade gpg-key
	slackpkg update gpg

	# upgrade package-list
	slackpkg update

	# upgrade slackpkg itself, should always be the first step, to prevent failures
	slackpkg upgrade slackpkg
	# upgrade glibc-solibs, should always be the second step, to prenvent failures
	slackpkg upgrade glibc-solibs
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

initrd_upg() {
	# get kernel version
	KVER=$(uname -r)

	echo ""
	echo "mkinitrd"

	# setup for generic kernel with xfs and root partion
	# rts_pstor = sd-card reader realtek firmware
	#
	# kernel drivers for acer 3830TG intel i5
	#i915 # intel graphics driver
	#mei_me # Communication controller
	#ehci_pci # USB controller
	#snd_hda_intel # Audio device
	#shpchp # PCI bridge
	#lpc_ich # ISA bridge
	#ahci # SATA controller
	#i2c_i801 # SMBus
	#atl1c # Ethernet controller
	#iwldvm # Network controller
	#rtsx_pci # Flash controller RTS5209
	#xhci_pci # USB controller
	#
	#-m xfs:btrfs:ntfs:kvm-intel:i915:mei_me:ehci_pci:snd-hda-intel:snd_hda_codec_conexant:snd_hda_codec_hdmi:shpchp:lpc_ich:ahci:i2c_i801:atl1c:iwldvm:rtsx_pci:xhci_pci:usbhid:hid_generic:acpi_cpufreq:nbd max_part=63 \
		#
		# i915, nvidia, bbswitch (ACPI calls for Optimus), bumblebee activ!
		#
	mkinitrd -c -u -L -R -k $KVER \
		 -m xfs:btrfs:ntfs:kvm-intel:i915:nvidia:bbswitch:mei_me:ehci_pci:shpchp:lpc_ich:ahci:i2c_i801:atl1c:iwldvm:rtsx_pci:xhci_pci:usbhid:hid_generic:nbd max_part=63 \
		 -f xfs \
		 -r /dev/sda2 \
		 -o /boot/initrd-$KVER.gz

	# wait until everything is finished
	sleep 5s

	# link too match lilo config
	ln -sf /boot/initrd-$KVER.gz /boot/initrd-generic.gz
	ln -sf /boot/vmlinuz-generic-$KVER /boot/vmlinuz-generic

	# link config and System.map to generic
	ln -sf /boot/System.map-generic-$KVER /boot/System.map
	ln -sf /boot/config-generic-$KVER /boot/config
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
		update
		inst_new
		;;
	upgr_all)
		update
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
		# stay with same kernel LTS and Nvidia
		#initrd_upg
		#boot_upg
		timestamp
		;;
	initrd_upg)
		initrd_upg
		boot_upg
		;;
	*)
		echo "usage: `basename $0` { update | inst_new | upgr_all | clean_sys | initrd_upg | sync }"
esac

