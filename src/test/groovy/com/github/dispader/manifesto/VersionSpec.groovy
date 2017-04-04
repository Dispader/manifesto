package com.github.dispader.manifesto

import spock.lang.*

class VersionSpec extends Specification {

    def 'handles no Git repository'() {
        given:
            def repository = Stub(Repository) {
                getExists() >> false
            }
        when:
            def version = new Version(repository)
        then:
            version.warningText == Version.MSG_NO_REPO
            version.versioned == false
            version.version == ''
    }

    def 'handles no Git commits'() {
        given:
            def repository = Stub(Repository) {
                getExists() >> true
                getHas_commits() >> false
            }
        when:
            def version = new Version(repository)
        then:
            version.warningText == Version.MSG_NO_COMMITS
            version.versioned == false
            version.version == ''
    }

    def 'handles no Git tags'() {
        given:
            def repository = Stub(Repository) {
                getExists() >> true
                getHas_commits() >> true
                getHas_tags() >> false
            }
        when:
            def version = new Version(repository)
        then:
            version.warningText == Version.MSG_NO_TAGS
            version.versioned == false
            version.version == ''
    }

}
