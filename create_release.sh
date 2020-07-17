#!/bin/bash

SCALA_MAJOR_VERSION="$1"
SCALA_MINOR_VERSION="$2"
REPO_FULL_NAME="$3"
TOKEN="$4"

releaseVersion="$(mvn -Dversion.scala.major="$SCALA_MAJOR_VERSION" -Dversion.scala.minor="$SCALA_MINOR_VERSION" -Drevision=$(git show -s --format=%ct.%h) help:evaluate -Dexpression=project.version -q -DforceStdout)"
message=$(git log -n 1 --pretty=format:'%s')
generate_post_data()
{
  cat <<EOF
{
  "tag_name": "$SCALA_MAJOR_VERSION-$releaseVersion",
  "target_commitish": "master",
  "name": "$SCALA_MAJOR_VERSION-$releaseVersion",
  "body": "$message",
  "draft": false,
  "prerelease": false
}
EOF
}


tag_release() {
	mvn -Dversion.scala.major="$SCALA_MAJOR_VERSION" -Dversion.scala.minor="$SCALA_MINOR_VERSION" -Drevision="$(git show -s --format=%ct.%h)" scm:tag
}

echo "Tagging release"
echo tag_release
echo "Create release $releaseVersion for repo: $REPO_FULL_NAME branch: master"
curl -H "Authorization: token $TOKEN" --data "$(generate_post_data)" "https://api.github.com/repos/$REPO_FULL_NAME/releases"
