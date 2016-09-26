package com.github.dispader.manifesto

import spock.lang.*

class VersionSpec extends Specification {

    def 'can determine if project is controlled by Git'() {
        when:
            Version.metaClass.static.getGit = { null }
        then:
            Version.version == '0.0.0'
            Version.versioned == false
    }

}
