#!/bin/sh

# add-readme-md5sums - Add README md5sums to old queues

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

# This should be run only on an up-to-date repository (no missing/orphaned
# queues) built before the README md5sums were added to the queues. Since I
# already ran it, nobody should need this :-).

# Find all READMEs
READMES=$(find /var/lib/sbopkg/SBo/13.0 -name README)

while read README; do
    QUEUE=../$(sed 's:.*/\([^/]*\)/README$:\1:g' <<< "$README").sqf

    # README md5sum already present in this one...
    grep -q '^#>README-MD5SUM ' $QUEUE && continue

    # Compute the MD5SUM
    echo "Checksumming README for $QUEUE..."
    MD5SUM=$(md5sum "$README" | cut -d' ' -f1)
    echo "#>README-MD5SUM $MD5SUM" >> $QUEUE
done <<< "$READMES"
