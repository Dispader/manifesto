package com.github.dispader.manifesto

class Version {

    private static getGit() { org.ajoberstar.grgit.Grgit.open() }

    static getVersioned() { ( Version.version == '0.0.0' ) ? false : true  }

    static getVersion() {
        String description = Version.git?.describe { }
        if ( !description ) { return '0.0.0' }
        description.startsWith('v') ? description.substring(1) : description
    }

    static getImplementation() { Version.version ?: '' }
    static getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

}
