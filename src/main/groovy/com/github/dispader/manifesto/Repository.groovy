package com.github.dispader.manifesto

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.errors.RepositoryNotFoundException

class Repository {

    private final String directory

    static Grgit getGrgit(String directory) {
        Grgit.open(dir: '.')
    }

    Repository(String directory) {
        try {
            Repository.getGrgit(directory)
        } catch(RepositoryNotFoundException exception) {
            throw new IllegalStateException('repository not found', exception)
        }
        this.directory = directory
    }

}
