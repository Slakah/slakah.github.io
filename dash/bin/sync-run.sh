#!/bin/bash
set -euo pipefail

readonly dest='james@jim-pi.local'

(cd .. \
  && rsync -vhra . $dest:~/Coding/slakah.github.io --include='**.gitignore' --exclude='/.git' --filter=':- .gitignore' --delete)
exec ssh -t $dest "source ~/.zshrc && cd ~/Coding/slakah.github.io/dash && $@"
