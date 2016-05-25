package com.github.dispader.manifesto

class Version {

    private static getGit() {
        try { org.ajoberstar.grgit.Grgit.open() } catch(Exception ex) { }
    }

    static getImplementation() { git?.describe { } ?: '' }
    static getSpecification() { implementation?.split(/-\d/)[0] ?: '' }

}
