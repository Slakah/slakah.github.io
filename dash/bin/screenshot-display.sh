#!/bin/bash
set -euo pipefail

echo 'taking screenshot...'
readonly resp=$(curl -sS -X POST http://localhost:3000/api/screenshot)
echo $resp
readonly status=$(echo $resp | jq -r .status)
if [[ "$status" != "ok" ]]; then
  echo "non-ok status $status, exiting"
  exit 1
fi
echo -e '\nupdating display...'
bin/display-update.sh output.png