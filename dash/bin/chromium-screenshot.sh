#!/bin/bash
set -euo pipefail

readonly url="$1"
readonly output="$2"
echo "taking screenshot of $1 to $2..."
exec chromium-browser --headless --no-sandbox --disable-gpu --hide-scrollbars --screenshot=$output --window-size=400x300 $url
