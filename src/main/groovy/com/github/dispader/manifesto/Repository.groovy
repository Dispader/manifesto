package com.github.dispader.manifesto

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.lib.RepositoryBuilder

class Repository {

    private directory

    Repository(String directory) { this.directory = directory }

    String getUrl() { jgitRepository().getConfig().getString("remote", 'origin', "url") }

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

    private jgitRepository() { new RepositoryBuilder().findGitDir(new File(directory)).build() }

    private grgitRepository() { org.ajoberstar.grgit.Grgit.open(directory) }

    Grgit getGrgitRepository() { grgitRepository() }

}
