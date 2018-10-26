package com.github.dispader.manifesto

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.RepositoryBuilder

class Repository {

    private final dir

    Repository(String directory) {
        if ( directory == null || !(new File(directory).isDirectory()) ) {
            throw new IllegalArgumentException()
        }
        this.dir = directory
    }

    Boolean getExists() { ( grgitRepository() != null ) }

    Boolean getHas_commits() {
        try {
          grgitRepository().log(maxCommits: 1)
          return true
        } catch (GrgitException) {
          return false
        }
    }

    Boolean getHas_tags() { ( !grgitRepository().tag.list().isEmpty() ) }

    Boolean getVersioned() { this.exists && this.has_commits && this.has_tags }

    String getDescribe() { grgitRepository().describe() }

    String getUrl() { jgitRepository().config.getString('remote', 'origin', 'url') }

    private jgitRepository() { new RepositoryBuilder().findGitDir(new File(dir)).build() }

    private grgitRepository() { Grgit.open(dir: dir) }

    Grgit getGrgitRepository() { grgitRepository() }

}
