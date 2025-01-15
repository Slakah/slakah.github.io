#!/bin/bash
set -euo pipefail
# script uses less memory than pnpm wrapper run
# for use on raspberry pi
exec node --max-old-space-size=128 --optimize-for-size dist/server.js