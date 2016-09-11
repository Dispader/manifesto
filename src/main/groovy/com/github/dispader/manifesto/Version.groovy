package com.github.dispader.manifesto

import org.gradle.api.GradleException

class Version {

    private static getGit() {
        try {
            org.ajoberstar.grgit.Grgit.open()
        } catch(Exception ex) {
            throw new GradleException('This project is not controlled by git.')
        }
    }

    static getVersion() {
        String description = git?.describe { }
        description.startsWith('v') ? description.substring(1) : description
    }

    static getImplementation() { Version.version ?: '' }
    static getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

}
