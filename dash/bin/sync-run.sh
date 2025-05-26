#!/bin/bash
set -euo pipefail

readonly dest='jamescollier@jim-pi.local'

if [ $# -eq 0 ]; then
  readonly command='sudo systemctl restart dash && sudo journalctl -u dash --since "1 hour ago" -f'
else
  readonly command="$@"
fi

echo 'building...'
pnpm build
echo 'syncing...'
(cd .. \
  && rsync -vhra . $dest:~/Coding/slakah.github.io --include='**.gitignore' --exclude='/.git' --filter=':- .gitignore' --delete \
  && rsync -vhra dash/dist/ $dest:~/Coding/slakah.github.io/dash/dist/ --delete)
echo 'running...'
exec ssh -t $dest "source ~/.zshrc && cd ~/Coding/slakah.github.io/dash && $command"
