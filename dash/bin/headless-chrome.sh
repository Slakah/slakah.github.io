#!/bin/bash
set -euo pipefail

if [ $# -eq 0 ]; then
  readonly url="http://localhost:3000"
else
  readonly url="$1"
fi

readonly response_status=$(curl -o /dev/null -s -w "%{http_code}" "$url")
if [ "200" -ne "$response_status" ]; then
    echo "got $response_status from $url"
fi

if [[ "$(uname)" == "Darwin" ]]; then
    readonly command="/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
else
    readonly command='chromium-browser'
fi

echo "starting headless chrome..."
exec "$command" --headless --remote-debugging-port=9222 --no-sandbox --disable-gpu --mute-audio --hide-scrollbars \
  --disable-extensions --disable-site-isolation-trials --disable-software-rasterizer \
  --disable-threaded-animation --disable-threaded-scrolling --disable-background-timer-throttling \
  --window-size=400x300 $url