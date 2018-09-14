#!/bin/bash
for i in {11..12}; do
	for j in {0..5}; do
	        #Old major releases
		mvn -Dgpg.passphrase="$GPG_PASSPHRASE" -Dversion.scalafmt=1.$j.0 -Dversion.scala=2.$i.6 -Dversion.scala.binary=2.$i clean deploy -Prelease
	done
	#Current point release
	mvn -Dgpg.passphrase="$GPG_PASSPHRASE" -Dversion.scalafmt=1.5.1 -Dversion.scala=2.$i.6 -Dversion.scala.binary=2.$i clean deploy -Prelease
done
