#!/bin/bash
set -euo pipefail

readonly url="$1"
readonly output="$2"

readonly response_status=$(curl -o /dev/null -s -w "%{http_code}" "$url")
if [ "200" -ne "$response_status" ]; then
    echo "got $response_status from $url"
fi

echo "taking screenshot of $url to $output..."
exec chromium-browser --headless --no-sandbox --disable-gpu --hide-scrollbars --screenshot=$output --window-size=400x300 $url
