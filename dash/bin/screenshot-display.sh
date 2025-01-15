#!/bin/bash
set -euo pipefail

echo 'taking screenshot...'
curl -sS -X POST http://localhost:3000/api/screenshot
echo -e '\nupdating display...'
bin/display-update.sh output.png