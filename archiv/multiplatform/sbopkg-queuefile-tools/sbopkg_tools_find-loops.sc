#!/bin/sh

# find-loops.sh - find dependency loops in a sbopkg queue collection

# Copyright 2010 Mauro Giachero <mauro.giachero@gmail.com>
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

# This script scans the queue archive looking for dependency loops (like
# A depends on B that depends on C that depends on A).
# Please keep in mind that when a loop is found we stop working a little
# early, so some loops may be unreported. After fixing the reported loops,
# run this script once more to see whether there are some more!


# Takes the queue for the last package and recurses on itself adding one new
# dependency. Loops over the new dependencies so that all of them are
# eventually added (but only one at a time).
#
# Input: a series of package names corresponding to queues.
# Output: * exit code 0, and no output, if no loop was detected
#         * exit code 1, and no output, if a loop was found but it doesn't
#           involve all the packages in the input list
#         * exit code 1 and the input parameters if a loop including the whole
#           list of packages was found.
resolve() {
  local PKGS LAST R P

  PKGS=$@
  LAST=${PKGS##* }
  while read R; do
    R=${R%%#*} # Drop comments
    R=${R%%|*} # Drop variables assignments
    [[ -z $R ]] && continue # Comments-only line
    [[ $R =~ '@.*' ]] || continue # Only follow queue references
    R=${R#@} # Remove queue reference.

    # Check if we already got this one
    for P in $PKGS; do
      if [[ $P = $R ]]; then
        # Loop found!
        if [[ $1 = $R ]]; then
          # Only report it if it has no initial out-of-the-loop packages
          # (if the loop is A-B-C-A, don't report X-Y-A-B-C-A).
          echo "$PKGS"
        fi
        # Bail out now (avoid following the loop)
        return 1
      fi
    done

    resolve $@ $R || return 1
  done < ../$LAST.sqf

  #[[ $# = 1 ]] && echo "Deps ok for $1"
}

# In some cases the loop is known and unremovable :-(
WHITELIST=("pyutil zbase32" "zbase32 pyutil")

QUEUES=$(ls ../*.sqf)
PACKAGES=$(sed 's:^.*/\(.*\)\.sqf$:\1:g' <<< "$QUEUES")
EXIT=0
for P in $PACKAGES; do
  LOOP=$(resolve $P)
  if [[ -n $LOOP ]]; then
    # Check whether the loop is in the whitelist
    WHITELISTED=
    for WHITE in "${WHITELIST[@]}"; do
      [[ $WHITE == $LOOP ]] && WHITELISTED=' [WHITELISTED]'
    done

    # If it's not in the whitelist, there's a problem...
    [[ -z $WHITELISTED ]] && EXIT=1

    # Report the loop (noting whether it's whitelisted)
    echo "Loop detected -- $LOOP$WHITELISTED"
  fi
done
exit $EXIT
