#!/bin/sh

# find-broken-references.sh - find broken package/queue references in a
# sbopkg queue collection

# Copyright 2010-2011 Mauro Giachero <mauro.giachero@gmail.com>
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

# This script scans the queue archive looking for broken queue references
# (these happen easily when packages are renamed).

add_regex_delete_known_package() {
  # Replace FOO with /^[^:]*:FOO$/d;
  sed 's/^\(.*\)$/\/\^\[\^:\]\*:\1\$\/d\;/g'
}

drop_comments() {
  sed "s/[ ]*#.*//g"
}

drop_queue_inclusion_symbol() {
  sed "s/@//g"
}

drop_build_flags() {
  sed "s/[ ]*|.*//g"
}

drop_empty_lines() {
  sed "/^[^:]*:[ ]*$/d"
}

drop_known_packages() {
  sed "$DELETE_KNOWN_PACKAGES_REGEX"
}

decorate_result() {
  sed "s/:\(.*\)$/: broken reference '\1'/"
}

QUEUES=$(ls ../*.sqf)
PACKAGES=$(sed 's:^.*/\(.*\)\.sqf$:\1:g' <<< "$QUEUES")
DELETE_KNOWN_PACKAGES_REGEX=$(echo "$PACKAGES" | add_regex_delete_known_package)
DELETE_KNOWN_PACKAGES_REGEX=$(echo $DELETE_KNOWN_PACKAGES_REGEX | tr -d ' ')
ERRORS=$(grep '' $QUEUES | drop_queue_inclusion_symbol | drop_comments | drop_build_flags | drop_empty_lines | drop_known_packages | decorate_result)

if [[ -n $ERRORS ]]; then
  echo "$ERRORS"

  echo 'To put the broken packages in the BROKEN directory run'
  echo -n "mkdir -p ../BROKEN && mv "
  echo -n $(cut -d: -f1 <<< "$ERRORS" | uniq)
  echo " ../BROKEN"
  echo "After that you'd better re-run $0"

  exit 1
fi
exit 0
