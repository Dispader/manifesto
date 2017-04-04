package com.github.dispader.manifesto

import org.ajoberstar.grgit.Grgit
import org.eclipse.jgit.errors.RepositoryNotFoundException

import spock.lang.Specification

public class RepositorySpec extends Specification {

    def 'fails if given a non-directory argument'() {
        when:
            def repository = new Repository(null)
        then:
            thrown(IllegalArgumentException)
    }

}
