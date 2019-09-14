package org.antipathy.mvn_scalafmt.model

import java.nio.file.{Path, Paths}

/**
  * Container class for a remote scalafmt config
  *
  * @param contents The contents of the config
  * @param location The local path where the config will be stored
  */
case class RemoteConfig(contents: String, location: Path = Paths.get(".scalafmt.conf"))
