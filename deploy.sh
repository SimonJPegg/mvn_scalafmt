#!/bin/bash
for j in {1..5}; do
        #Old major releases
    mvn -Dgpg.passphrase="$GPG_PASSPHRASE" -Dversion.scalafmt=1.$j.0 clean deploy -Prelease
done
#Current point release
mvn -Dgpg.passphrase="$GPG_PASSPHRASE" -Dversion.scalafmt=1.5.1 clean deploy -Prelease

