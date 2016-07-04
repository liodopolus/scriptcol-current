#!/bin/bash
# written by Jeffrey Scherling
# rsync KDE_5

# URLs
# sync://alien.slackbook.org/alien/ktown/current/5 .
# http://slackware.org.uk/people/alien-kde/current/testing/x86_64/

RSYNCURL="rsync://slackware.org.uk/people/alien-kde/current/5" 

#pull() {
#rsync   -avz --delete --progress --exclude=x86 \
#	--delete-excluded \
#	--exclude="kde-l10n-[a-c,e-z][a-e,f-z]" \
#	--exclude="kde-l10n-da" \
#	--exclude="kde-l10n-en_GB" \
#	--exclude="kde-l10n-nds" \
#	--exclude="kde-l10n-pt_BR" \
#	--exclude="kde-l10n-zh_CN" \
#	--exclude="kde-l10n-zh_TW" \
#${RSYNCURL} .
#}

	#transform links in dir: --copy-links \
rsync \
	-avz \
	--delete \
	--delete-excluded \
	--progress \
	--exclude "source" \
	--exclude "x86" \
	--exclude "telepathy" \
	--include "kdei/kde-l10n-de*" \
	--include "kdei/calligra-l10n-de*" \
	--exclude "kdei/*.txz" \
	--exclude "kdei/*.txt" \
	--exclude "kdei/*.asc" \
	--exclude "kdei/*.md5" \
	--exclude "kdei/*.lst" \
	--exclude "kdei/*.meta" \
	--links \
	--copy-links \
$RSYNCURL .

echo -e "\n RSYNC [ KDE_5 ] \t\t [\033[1;35m  OKAY  \033[0m]\n"

