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

    String getUrl() { jgitRepository().config.getString('remote', 'origin', 'url') }

    String getDescribe() { grgitRepository().describe() }

    Boolean getHasRepo() { ( grgitRepository() != null ) }

    Boolean getHasCommits() {
        try {
          grgitRepository().log(maxCommits: 1)
          return true
        } catch (GrgitException) {
          return false
        }
    }

    Boolean getHasTags() { ( !grgitRepository().tag.list().isEmpty() ) }

    private jgitRepository() { new RepositoryBuilder().findGitDir(new File(dir)).build() }

    private grgitRepository() { Grgit.open(dir) }

    Grgit getGrgitRepository() { grgitRepository() }

}