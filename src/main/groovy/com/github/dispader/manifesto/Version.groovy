package com.github.dispader.manifesto

class Version {
    static String MSG_NO_REPO = "The Manifesto plugin is only useful for projects using git source control."
    static String MSG_NO_COMMITS = "The Manifesto plugin couldn't find any git commits."
    static String MSG_NO_TAGS = "The Manifesto plugin couldn't find any version tags. You can create your first tag with `git tag -a v0.1.0`."

    private static getGit() {
        try {
            org.ajoberstar.grgit.Grgit.open()
        } catch (IllegalArgumentException) {
            null
        }
    }

    static getVersioned() {
       ( Version.hasRepo && Version.hasCommits && Version.hasTags )
    }

    static getVersion() {
        if ( !Version.versioned ) { return null }
        String description = Version.git?.describe()
        description.startsWith('v') ? description.substring(1) : description
    }

    static getImplementation() { Version.version ?: '' }
    static getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

    static getWarningText() {
        if ( !Version.hasRepo ) { return MSG_NO_REPO }
        if ( !Version.hasCommits ) { return MSG_NO_COMMITS }
        if ( !Version.hasTags ) { return MSG_NO_TAGS }
    }

    private static getHasRepo() { ( Version.git != null ) }

    private static getHasCommits() {
        try {
          Version.git.log(maxCommits: 1)
          return true
        } catch (GrgitException) {
          return false
        }
    }

    private static getHasTags() { ( !Version.git.tag.list().isEmpty() ) }
}
