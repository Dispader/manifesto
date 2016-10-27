package com.github.dispader.manifesto

import spock.lang.*

class VersionSpec extends Specification {

    def 'handles no Git repository'() {
        when:
            Version.metaClass.static.getHasRepo = { false }
        then:
            Version.warningText == Version.MSG_NO_REPO
            Version.versioned == false
            Version.version == null
    }

    def 'handles no Git commits'() {
        when:
            Version.metaClass.static.getHasRepo = { true }
            Version.metaClass.static.getHasCommits = { false }
        then:
            Version.warningText == Version.MSG_NO_COMMITS
            Version.versioned == false
            Version.version == null
    }

    def 'handles no Git tags'() {
        when:
            Version.metaClass.static.getHasRepo = { true }
            Version.metaClass.static.getHasCommits = { true }
            Version.metaClass.static.getHasTags = { false }
        then:
            Version.warningText == Version.MSG_NO_TAGS
            Version.versioned == false
            Version.version == null
    }

}
