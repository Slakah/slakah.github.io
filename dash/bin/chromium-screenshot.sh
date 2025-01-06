#!/bin/bash
set -euo pipefail

readonly url="$1"
readonly output="$2"

readonly response_status=$(curl -o /dev/null -s -w "%{http_code}" "$url")
if [ "200" -ne "$response_status" ]; then
    echo "got $response_status from $url"
fi

echo "taking screenshot of $url to $output..."
if [[ "$(uname)" == "Darwin" ]]; then
    readonly command="/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
else
    readonly command='chromium-browser'
fi
exec "$command" --headless --no-sandbox --disable-gpu --mute-audio --hide-scrollbars \
  --disable-extensions --disable-site-isolation-trials --disable-software-rasterizer \
  --disable-threaded-animation --disable-threaded-scrolling --disable-background-timer-throttling \
  --screenshot=$output --window-size=400x300 $url
