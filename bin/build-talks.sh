#!/bin/bash
set -euo pipefail

rm -rf website/static/talks

# mdoc 2.0.3 can't discover its classpath on JDK 9+, so pass it explicitly
CLASSPATH_DEPS="org.typelevel:cats-effect_2.12:2.0.0 \
  org.typelevel:cats-effect-laws_2.12:2.0.0 \
  com.github.alexarchambault:scalacheck-shapeless_1.14_2.12:1.2.3 \
  co.fs2:fs2-core_2.12:2.1.0 \
  org.scalacheck:scalacheck_2.12:1.14.1"
# shellcheck disable=SC2086
TALKS_CLASSPATH=$(coursier fetch --classpath org.scalameta:mdoc_2.12:2.0.3 $CLASSPATH_DEPS)

# shellcheck disable=SC2086
coursier launch org.scalameta:mdoc_2.12:2.0.3 $CLASSPATH_DEPS \
  -- --in talks --out website/static/talks \
  --classpath "$TALKS_CLASSPATH" \
  --scalac-options "-Ypartial-unification"

jq -n --rawfile content website/static/talks/fs2-fake-it.md \
    '{"content": $content, "title": "FS2 - Fake it till you make it"}' \
  | mustache - talks/index.html.mustache \
  > website/static/talks/fs2-fake-it.html

jq -n --rawfile content website/static/talks/cats-the-musical-effect.md \
    '{"content": $content, "title": "Cats the musical effect"}' \
  | mustache - talks/index.html.mustache \
  > website/static/talks/cats-the-musical-effect.html

rm website/static/talks/*.md website/static/talks/*.json website/static/talks/index.html.mustache
