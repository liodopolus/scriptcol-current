#!/bin/bash
# Copyright 2012 Christoph Langner
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Get url to asx video stream out of the no flash version of the zdf mediathek
url_noflash="$1"
url_stream=$(curl -s ${url_noflash} | grep asx | grep -o 'http://[^"]*')

# Check if No Flash Mediathek is used
if [[ "${url_noflash}" != *flash=off* ]]
then
 	echo "Die übergebene URL zeigt nicht auf die HTML-Version der Mediathek!";
 	echo "Bitte klicke auf der Webseite der Mediathek links unten auf \"HTML-Version\" ";
	echo "oder öffne die folgende URL im Browser:"
	echo "http://www.zdf.de/ZDFmediathek/hauptnavigation/startseite?flash=off"
	exit
fi

# If filename is empty, get name out of url
filename=$2
if [ -z ${filename} ]; then
	filename=$(echo ${url_noflash} | cut -d/ -f8- | cut -d? -f1)".wmv"
fi 

# Get stream and save it to disk
echo "Lade ${filename} herunter, bitte haben Sie etwas Geduld..."
mplayer -really-quiet -nolirc -playlist ${url_stream} -dumpstream -dumpfile ${filename}

