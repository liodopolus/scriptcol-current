#!/bin/bash
# written by Jeffrey Scherling
#
# rsync from delta to here

# Pull data from delta
#rsync -avz --progress delta:/root/RSYNC.gamma/ .

# Push data to delta
#rsync -avz --progress . delta:/root/RSYNC.gamma/ 

# Sync data between gamma and delta
rsync -avz --delete --progress --exclude="rsync-delta.sc" . delta:/root/RSYNC.gamma/ 

