#!/bin/bash
set -euo pipefail

readonly output="$1"

# only update display when file changed
readonly hashFile="$output.hash"
readonly outputHash=$(sha256sum $output)
if [ -f "$hashFile" ]; then
  readonly oldHash="$(cat $hashFile)"
  if [ "$outputHash" == "$oldHash" ]; then
    echo "$output not changed, exiting"
    exit 0
  fi
fi
echo "$outputHash" > $hashFile

echo "updating display to $output..."
exec poetry run python bin/image.py -i $output