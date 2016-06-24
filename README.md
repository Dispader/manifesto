# Manifesto plugin

A plugin for the Gradle build system that sets manifest data for JAR and WAR artifacts inferred from a project's Git status.

## Usage

### Applying the plugin

Include either of the following in your build script:

#### Gradle 2.1+

```groovy
plugins {
    id 'com.github.dispader.manifesto' version '0.2.0'
}
```

#### Gradle 1.x/2.0

```groovy
buildscript {
    repositories.jcenter()
    dependencies {
        classpath 'com.github.dispader:manifesto:0.2.0'
    }
}
apply plugin: 'com.github.dispader.manifesto'
```

### Prerequisites

The plugin will only works in conjunction with the `java` and/or `war` plugin, and only produces meaningful results for Git projects.

### Configuration

Some elements of manifests which cannot be determined from the Git project status may be set via the plugin's configuration object.

* `vendor` sets `Specification-Vendor` and `Implementation-Vendor`
* `vendor_id` sets `Implementation-Vendor-Id`
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
* `Implementation-Timestamp` is set to the build time
