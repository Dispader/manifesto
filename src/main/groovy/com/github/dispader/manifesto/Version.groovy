package com.github.dispader.manifesto

class Version {

    final static String MSG_NO_REPO = 'The Manifesto plugin is only useful for projects using git source control.'
    final static String MSG_NO_COMMITS = 'The Manifesto plugin couldn\'t find any git commits.'
    final static String MSG_NO_TAGS = 'The Manifesto plugin couldn\'t find any version tags. You can create your first tag with `git tag -a v0.1.0`.'

    private final Repository repository

    private Version(Repository repository) {
        this.repository = repository
    }

    Version() {
        repository = new Repository('.')
    }

    String getVersion() {
        if ( !repository.versioned ) { return '' }
        String description = repository?.describe
        description.startsWith('v') ? description[1..-1] : description
    }

    String getImplementation() {
        this.version ?: ''
    }

    String getSpecification() {
        implementation?.split(/-\d/)[0] ?: ''
    }

    String getUrl() {
        !(repository.url ==~ /git@([\w\.]+):([\w\/])+\.git/) ? '' : toUrl(repository.url)
    }

    def toUrl(String git_ssh) {
        'http://HOST/REPO'
        host(git_ssh)
    }

    private String host(String git_ssh) {
        (git_ssh =~ /git@([\w\.]+):([\w\/])+\.git/)[1]
    }

    String getWarningText() {
        if ( !repository.exists ) { return MSG_NO_REPO }
        if ( !repository.has_commits ) { return MSG_NO_COMMITS }
        if ( !repository.has_tags ) { return MSG_NO_TAGS }
    }

}
