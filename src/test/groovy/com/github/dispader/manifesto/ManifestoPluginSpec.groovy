package com.github.dispader.manifesto

import spock.lang.*
import org.gradle.api.*
import org.gradle.testfixtures.*

class ManifestoPluginSpec extends Specification {

    private Project project = ProjectBuilder.builder().build()

    def '(plugins: java) JAr manifest contains a version'() {
        given:
            project.pluginManager.apply 'java'
        expect:
            project.tasks.jar.manifest.attributes['Manifest-Version'] == '1.0'
    }

    def '(plugins: manifesto, java) adds manifest attributes to the JAr'() {
        given:
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.pluginManager.apply 'java'
            def attributes = project.tasks.jar.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Manifest-Version'         | '1.0'
            'Specification-Title'      | 'test'
            'Specification-Version'    | /\d+\.\d+\.\d/
            'Implementation-Title'     | 'test'
            'Implementation-Version'   | /\d+\.\d+\.\d(-.*)?/
            'Implementation-Timestamp' | /.+/
    }

    def '(plugins: java, manifesto) adds manifest attributes to the JAr'() {
        given:
            project.pluginManager.apply 'java'
            project.pluginManager.apply 'com.github.dispader.manifesto'
            def attributes = project.tasks.jar.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Manifest-Version'         | '1.0'
            'Specification-Title'      | 'test'
            'Specification-Version'    | /\d+\.\d+\.\d/
            'Implementation-Title'     | 'test'
            'Implementation-Version'   | /\d+\.\d+\.\d(-.*)?/
            'Implementation-Timestamp' | /.+/
    }

    def '(plugins: war, manifesto) adds manifest attributes to the JAr'() {
        given:
            project.pluginManager.apply 'war'
            project.pluginManager.apply 'com.github.dispader.manifesto'
            def attributes = project.tasks.jar.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Manifest-Version'         | '1.0'
            'Specification-Title'      | 'test'
            'Specification-Version'    | /\d+\.\d+\.\d/
            'Implementation-Title'     | 'test'
            'Implementation-Version'   | /\d+\.\d+\.\d(-.*)?/
            'Implementation-Timestamp' | /.+/
    }

    def '(plugins: war, manifesto) adds manifest attributes to the WAr'() {
        given:
            project.pluginManager.apply 'war'
            project.pluginManager.apply 'com.github.dispader.manifesto'
            def attributes = project.tasks.war.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Manifest-Version'         | '1.0'
            'Specification-Title'      | 'test'
            'Specification-Version'    | /\d+\.\d+\.\d/
            'Implementation-Title'     | 'test'
            'Implementation-Version'   | /\d+\.\d+\.\d(-.*)?/
            'Implementation-Timestamp' | /.+/
    }

}
