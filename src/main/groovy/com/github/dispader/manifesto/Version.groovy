package com.github.dispader.manifesto

import org.gradle.api.ProjectConfigurationException

class Version {

    private static getGit() { org.ajoberstar.grgit.Grgit.open() }

    static Boolean getOkay() { Version.git as Boolean }

    static getVersion() {
        String description
        if ( !Version.okay ) '0.0.0'
        description = git?.describe { }
        if ( !description ) {
            throw new ProjectConfigurationException('Marx failed you.', null)
        }
        description.startsWith('v') ? description.substring(1) : description
    }

    static getImplementation() { Version.version ?: '' }
    static getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

}
