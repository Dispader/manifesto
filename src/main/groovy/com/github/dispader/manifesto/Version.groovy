package com.github.dispader.manifesto

class Version {

    final static String MSG_NO_REPO = 'The Manifesto plugin is only useful for projects using git source control.'
    final static String MSG_NO_COMMITS = 'The Manifesto plugin couldn\'t find any git commits.'
    final static String MSG_NO_TAGS = 'The Manifesto plugin couldn\'t find any version tags. You can create your first tag with `git tag -a v0.1.0`.'

    private final Repository repository

    Version() {
        repository = new Repository('.')
    }
    
    private Version(Repository repository) {
        this.repository = repository
    }

    Boolean getVersioned() {
       ( repository.exists && repository.has_commits && repository.has_tags )
    }

    String getVersion() {
        if ( !this.versioned ) { return null }
        String description = repository?.describe
        description.startsWith('v') ? description[1..-1] : description
    }

    String getImplementation() { this.version ?: '' }

    String getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

    String getWarningText() {
        if ( !repository.exists ) { return MSG_NO_REPO }
        if ( !repository.has_commits ) { return MSG_NO_COMMITS }
        if ( !repository.has_tags ) { return MSG_NO_TAGS }
    }

}
