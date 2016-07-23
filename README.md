# Manifesto plugin

![Release](https://img.shields.io/badge/version-0.3.0-blue.svg) [![Build Status](https://travis-ci.org/Dispader/manifesto.svg?branch=master)](https://travis-ci.org/Dispader/manifesto) [![Dependency Status](https://www.versioneye.com/user/projects/576d52ad7bc681003c4900aa/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/576d52ad7bc681003c4900aa)

A plugin for the Gradle build system that sets manifest data for JAR and WAR artifacts inferred from a project's Git status.

## Usage

### Applying the plugin

Include either of the following in your build script:

#### Gradle 2.1+

```groovy
plugins {
    id 'com.github.dispader.manifesto' version '0.3.0'
}
```

#### Gradle 1.x/2.0

```groovy
buildscript {
    repositories.jcenter()
    dependencies {
        classpath 'com.github.dispader:manifesto:0.3.0'
    }
}
apply plugin: 'com.github.dispader.manifesto'
```

### Prerequisites

The plugin only affects configurations for the `java` and/or `war` plugins, and only produces meaningful results for Git projects.

### Configuration

#### default configuration

If the conventional `project.group` is defined (for the top-level project), this value will be used to set the `Implementation-Vendor-Id`.

#### extension configuration

Some elements of manifests which cannot be determined from the Git project status or the default project configuration can be set via the plugin's configuration object.

* `vendor` sets `Specification-Vendor` and `Implementation-Vendor`
* `vendor_id` sets `Implementation-Vendor-Id` (and overrides `project.group` default)
* `url` sets `Implementation-URL`

```groovy
manifesto {
    vendor = 'Jake Gage'
    vendor_id = 'com.github.dispader'
    url = 'https://github.com/Dispader/manifesto'
}
```

### Behavior

* `Manifest-Version` is set to `1.0`
* `Specification-Title` and `Implementation-Title` are set to the Git project directory name
* `Specification-Version` and `Implementation-Version` are set based on Git commit status (see [`git describe`](https://git-scm.com/docs/git-describe))
  * IFF no version can be determined via a `git describe`, and a `rootProject.version`, this value will be used instead
* `Implementation-Timestamp` is set to the build time

[![Analytics](https://ga-beacon.appspot.com/UA-61184208-1/chromeskel_a/readme)](https://github.com/igrigorik/ga-beacon)
