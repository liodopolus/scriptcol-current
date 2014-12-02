#!/bin/bash
# test positional parameters
echo "Total number of arguments: $#"
echo "Total number of arguments: $@"
echo "Argument 1: $1"
echo "Argument 2: $2"
echo "Argument 3: $3"
echo "Argument 4: $4"
echo "Argument 5: $5"

echo "ab Argument 3: ${@: +3}"
