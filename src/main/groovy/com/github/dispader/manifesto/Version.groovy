package com.github.dispader.manifesto

class Version {

    private static getGit() {
        try { org.ajoberstar.grgit.Grgit.open() } catch(Exception ex) { }
    }

    static getVersion() {
        String description = git?.describe { }
        description.startsWith('v') ? description.substring(1) : description
    }

    static getImplementation() { Version.version ?: '' }
    static getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

}
