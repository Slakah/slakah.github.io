#!/bin/bash
set -euo pipefail

readonly dest='james@jim-pi.local'

rsync -vhra . $dest:~/Coding/slakah.github.io/dash --include='**.gitignore' --exclude='/.git' --filter=':- .gitignore' --delete-after
ssh $dest "cd ~/Coding/slakah.github.io/dash && pnpm run serve"
