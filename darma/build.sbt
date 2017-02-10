import sbt.Keys._

name := "darma"

version := "1.0-SNAPSHOT"

javacOptions ++= Seq("-encoding", "UTF-8")

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.apache.commons" % "commons-email" % "1.3.1",
  "commons-io" % "commons-io" % "2.3",
  "org.springframework" % "spring-context" % "4.1.1.RELEASE",
  "org.springframework" % "spring-orm" % "4.1.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.1.1.RELEASE",
  "org.springframework" % "spring-tx" % "4.1.1.RELEASE",
  "org.springframework" % "spring-expression" % "4.1.1.RELEASE",
  "org.springframework" % "spring-aop" % "4.1.1.RELEASE",
  "org.springframework" % "spring-test" % "4.1.1.RELEASE" % "test",
  "commons-validator" % "commons-validator" % "1.4.1",
  filters
)

play.Project.playJavaSettings
