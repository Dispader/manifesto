# Manifesto

Development version of a Gradle plugin to set manifest for versioned (JAR, WAR)
artifacts based on implied (project/Git) status.

## usage

* `apply plugin: "<path_to_plugin>"`
  * `./gradlew manifesto` - test
  * `./gradlew manifestJar` - create JAr with populated manifest
  * `./gradlew manifestWar` - create WAr with populated manifest
