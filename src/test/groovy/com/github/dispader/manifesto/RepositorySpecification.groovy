package com.github.dispader.manifesto

import org.eclipse.jgit.errors.RepositoryNotFoundException

import spock.lang.Specification

public class RepositorySpecification extends Specification {

    def 'will not construct an inavalid repository'() {
        given:
            Repository.metaClass.static.getGrgit = { String directory ->
                throw new RepositoryNotFoundException(directory)
            }
        when:
            def repository = new Repository('.')
        then:
            thrown(IllegalStateException)
    }

    def 'can construct a valid repository'() {
        given:
            Repository.metaClass.static.getGrgit = { String directory -> }
        when:
            def repository = new Repository('.')
        then:
            notThrown(IllegalStateException)
    }

}
