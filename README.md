# Manifesto

Development version of a Gradle plugin to set manifest for versioned (JAR, WAR)
artifacts based on implied (project/Git) status.

## usage

* `./gradlew test` — run tests
* `./gradlew jar` — package plugin
* `./gradlew uploadArchives` — store packaged JAr in temporary repository
  * in `../repo` (may be used for local testing)
  * TODO: remove
* `apply plugin: "<path_to_plugin>"` — applies plugin to project
  * adds Manifest information to JAr and WAr archives produced by plugins
