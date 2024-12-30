#!/bin/bash
set -euo pipefail

readonly url="$1"
readonly output="$2"
bin/chromium-screenshot.sh $url $output
poetry run python bin/image.py -i $output