#!/bin/bash
set -euo pipefail

readonly source='jamescollier@jim-pi.local'

if [ $# -eq 0 ]; then
  readonly command='sudo systemctl restart dash && sudo journalctl -u dash --since "1 hour ago" -f'
else
  readonly command="$@"
fi

echo 'syncing from pi to local...'
rsync -vhra $source:/etc/systemd/system/dash.service ./system/dash.service
rsync -vhra $source:/etc/systemd/system/headless-chrome.service ./system/headless-chrome.service
ssh jamescollier@jim-pi.local "crontab -l" > ./system/crontab

