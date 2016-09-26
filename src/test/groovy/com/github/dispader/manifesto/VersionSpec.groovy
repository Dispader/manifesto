package com.github.dispader.manifesto

import spock.lang.*

class VersionSpec extends Specification {

    def 'can determine if project is controlled by Git'() {
        expect: Version.okay == true
    }

}
