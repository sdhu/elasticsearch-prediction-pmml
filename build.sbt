organization := "com.sdhu"

name := "elasticsearchprediction-pmml"

version := "0.1"

scalaVersion := "2.10.4"

val pmmlV = "1.0.21"

scalacOptions ++= Seq(
  "-deprecation", 
  "-encoding", 
  "UTF-8", 
  "-feature", 
  "-unchecked",
  "-Ywarn-adapted-args", 
  "-Ywarn-value-discard", 
  "-Xlint")

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "org.jpmml"           % "pmml-evaluator"          % pmmlV,
  "org.jpmml"           % "pmml-model"              % pmmlV,
  "org.scalatest"       %% "scalatest"              % "2.2.4" % "test" withSources()
)



licenses := Seq("Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

//logLevel := Level.Warn

//logLevel in compile := Level.Warn

cancelable := true

parallelExecution in ThisBuild := false
