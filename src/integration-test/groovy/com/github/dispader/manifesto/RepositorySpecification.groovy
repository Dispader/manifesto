package com.github.dispader.manifesto

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.errors.RepositoryNotFoundException

import spock.lang.Specification

public class RepositorySpecification extends Specification {

    def repository = new Repository('.')

    def 'can determine the remote for a repository'() {
        expect:
            repository.url == 'git@github.com:Dispader/manifesto.git'
    }

    def 'can describe a repository'() {
        expect:
            repository.describe ==~ /v?\d+\.\d+\.\d(-.*)?/
    }

    def 'can report that a repository has commits'() {
        expect:
            repository.hasCommits == true
    }

    def 'can report that a repository has tags'() {
        expect:
            repository.hasTags == true
    }

}
