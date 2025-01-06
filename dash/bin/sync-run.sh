#!/bin/bash
set -euo pipefail

readonly dest='james@jim-pi.local'

(cd .. \
  && rsync -vhra . $dest:~/Coding/slakah.github.io --include='**.gitignore' --exclude='/.git' --filter=':- .gitignore' --delete \
  && rsync -vhra dash/dist/ $dest:~/Coding/slakah.github.io/dash/dist/ --delete)
exec ssh -t $dest "source ~/.zshrc && cd ~/Coding/slakah.github.io/dash && $@"
