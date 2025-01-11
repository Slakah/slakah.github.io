#!/bin/bash
set -euo pipefail

usage() {
  echo "Usage: $0 {encrypt|decrypt|cat}"
  exit 1
}

if [ $# -eq 0 ]; then
  usage
fi

readonly command="$1"
shift
# 1password - "Dash OpenSSL Passphrase"
readonly key="$(jq .secretKey env.json)"
readonly opensslOpts='-aes-256-cbc -pbkdf2 -salt'

case "$command" in
  encrypt)
    openssl enc $opensslOpts -in secrets.json -out secrets.json.enc -k "$key"
    exit 0
    ;;
  decrypt)
    openssl enc $opensslOpts -d -in secrets.json.enc -out secrets.json -k "$key"
    exit 0
    ;;
  cat)
    openssl enc $opensslOpts -d -in secrets.json.enc -k "$key"
    exit 0
    ;;
  *)
    usage
    ;;
esac