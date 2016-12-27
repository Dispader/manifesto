package com.github.dispader.manifesto

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.errors.RepositoryNotFoundException

import spock.lang.Specification

public class RepositorySpecification extends Specification {

    def setup() {
        GroovyMock(Repository, global: true)
    }

    def 'will not construct an inavalid repository (mock)'() {
        given:
            Repository.getGrgit(_) >> {
                throw new RepositoryNotFoundException(directory)
            }
        when:
            def repository = new Repository('.')
        then:
            notThrown(IllegalStateException)
    }

    def 'can construct a valid repository (mock)'() {
        given:
            Repository.getGrgit(_) >> { }
        when:
            def repository = new Repository('.')
        then:
            notThrown(IllegalStateException)
    }

}
