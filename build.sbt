scalaVersion := "2.13.8"

// Set to false or remove if you want to show stubs as linking errors
nativeLinkStubs := true

enablePlugins(ScalaNativePlugin)

import scala.scalanative.build._

libraryDependencies ++= Seq(
    "dev.zio" %%% "zio" % "1.0.13",
    "dev.zio" %%% "zio-streams" % "1.0.13",
    "io.github.cquiroz" %%% "scala-java-time" % "2.4.0-M2"
)

