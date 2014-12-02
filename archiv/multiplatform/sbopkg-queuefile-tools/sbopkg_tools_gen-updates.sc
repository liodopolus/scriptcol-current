#!/bin/sh

# gen-updates - Generate 90%-good sbopkg queue files.

# Copyright 2009 Mauro Giachero <mauro.giachero@gmail.com>
#
# Redistribution and use of this script, with or without modification, is
# permitted provided that the following conditions are met:
#
# 1. Redistributions of this script must retain the above copyright notice,
# this list of conditions and the following disclaimer.
#
#  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
#  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
#  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN
#  NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
#  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
#  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
#  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
#  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
#  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
#  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

# Build queues for new packages, and report orphaned queues.
# Notes:
# - all the queues generated _must_be_ manually validated. 10% of them is
#   wrong for a reason or another
# - to remove the READMEs from the queue files after validation, run
#   cleanup-comments.sh

# Find all READMEs
READMES=$(find /var/lib/sbopkg/SBo/13.37 -mindepth 3 -maxdepth 3 -name README)

# Extract all package names
NAMES=$(sed 's:.*/\([^/]*\)/README$:\1:g' <<< "$READMES")

# Find all known queue files
QUEUES=$(find ../ -maxdepth 1 -name \*.sqf)

COUNT=$(wc -l <<< "$NAMES")
DONE=0

build_queue_for() {
    local NAME=$1
    local README=$2
    local TEMPFILE=$(tempfile)
    local RESULT
    
    # While extracting package names from the README, also take care of "jack"
    # which is a very common shortcut for "jack-audio-connection-kit".
    cat $README | tr -c _a-zA-Z0-9-+/ ' ' | tr " " "\n" | sed 's:^jack$:jack-audio-connection-kit:g' | sort | uniq |
        while read P; do
            [[ -z $P ]] && continue

            Q=$(grep -xi -m1 -- $P <<< "$NAMES")

            # Only pick package names from the READMEs.
            # The list of packages on the 2nd line is of common false
            # positives I got bored removing from the queues.
            shopt -s nocasematch
            if  [[ $Q ]] &&
                [[ $P != R && $P != joystick && $P != framework && $P != dump && $P != empty && $P != smart && $P != patches && $P != mac && $P != task && $P != transmission && $P != ascii && $P != an ]] &&
                [[ $P != $NAME ]] &&
                ! grep -qxi -- "@$P" $TEMPFILE; then

                    echo "@$Q" >> $TEMPFILE
            fi
            shopt -u nocasematch
        done
    cat $TEMPFILE | sort
    echo $NAME
    rm -f $TEMPFILE    
}

# Build a preliminary queue for new packages
while read NAME; do
    echo -ne [$DONE/$COUNT]\\r

    README=$(grep /$NAME/README\$ <<< "$READMES")
    MD5SUM=$(md5sum "$README" | cut -d' ' -f1)

    # Check queue existence
    if grep -q /$NAME.sqf\$ <<< "$QUEUES"; then
        # Since the queue exists, remove it from QUEUES
        QUEUES=$(sed "/\/$NAME.sqf/d" <<< "$QUEUES")
        # Verify the README md5sum
        MD5SUM_OLD=$(grep '^#>README-MD5SUM ' ../$NAME.sqf | cut -d' ' -f2)
        if [[ $MD5SUM_OLD != $MD5SUM ]]; then
            # Since the README changed we need to audit this one
            echo -n "Building verification queue for updated package $NAME... "

            QUEUE_NEW=$(build_queue_for $NAME $README)
            QUEUE_OLD=$(cat ../$NAME.sqf | grep -v '^#>README-MD5SUM ' | grep -v "^#")
            
            # Notify the README update
            echo '## *** README UPDATED ***' > $NAME.sqf
            echo '##' >> $NAME.sqf

            # Add the README as a comment to the new queue
            cat $README | sed 's:^:## :g' >> $NAME.sqf
            echo '##' >> $NAME.sqf

            echo '## *** THIS IS THE NEW GUESSED QUEUE ***' >> $NAME.sqf
            echo '##' >> $NAME.sqf
            echo "$QUEUE_NEW" >> $NAME.sqf
            echo '##' >> $NAME.sqf
            if [[ $QUEUE_NEW = $QUEUE_OLD ]]; then
                echo '## *** THE OLD AND NEW QUEUES ARE EQUAL ***' >> $NAME.sqf
            else
                echo '## *** THIS IS THE OLD QUEUE ***' >> $NAME.sqf
                echo "$QUEUE_OLD" >> $NAME.sqf
            fi
            # Add the md5sum of the new README
            echo "#>README-MD5SUM $MD5SUM" >> $NAME.sqf
            echo 'done.'
        fi
    else
        echo -n "Building queue for new package $NAME... "
        rm -f $NAME.sqf

        # Extract an initial queue parsing the README
        cat $README | sed 's:^:## :g' >> $NAME.sqf
        echo '##' >> $NAME.sqf
        echo '##' >> $NAME.sqf

        build_queue_for $NAME $README >> $NAME.sqf
  
        # Add the md5sum of the README
        echo "#>README-MD5SUM $MD5SUM" >> $NAME.sqf

        echo 'done.'
    fi

    ((DONE++))
done <<< "$NAMES"

# Report orphaned queues
if [[ -n $QUEUES ]]; then
    echo "The following queue files seem orphaned:"
    echo "$QUEUES"
fi
