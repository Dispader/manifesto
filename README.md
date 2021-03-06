 # Manifesto plugin

[![Release](https://img.shields.io/badge/version-1.0.13-blue.svg)](https://plugins.gradle.org/plugin/com.github.dispader.manifesto) [![Build Status](https://travis-ci.org/Dispader/manifesto.svg?branch=master)](https://travis-ci.org/Dispader/manifesto)

A plugin for the Gradle build system that sets manifest data for JAr and WAr artifacts inferred from a project's Git status.

## Usage

### Applying the plugin

```groovy
plugins {
    id 'com.github.dispader.manifesto' version '1.0.13'
}
```

### Prerequisites

The plugin only affects configurations for the `groovy`, `java`, and `war` plugins; and only produces meaningful results for Git projects.

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
* `Specification-Title` and `Implementation-Title` are set to the Gradle `rootProject.name`
* `Specification-Version` and `Implementation-Version` are set based on Git commit status (see [`git describe`](https://git-scm.com/docs/git-describe))
  * IFF no version can be determined via a `git describe`, and a Gradle `rootProject.version` can be, this value will be used instead
* `Implementation-Timestamp` is set to the build time
