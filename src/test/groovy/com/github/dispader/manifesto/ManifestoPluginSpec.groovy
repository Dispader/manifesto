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

    def '(plugins: manifesto) accepts configuration extensions'() {
        given:
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.configure(project) {
                manifesto {
                    vendor = 'Jake Gage'
                    vendor_id = 'com.github.dispader'
                    url = 'https://github.com/Dispader/manifesto'
                }
            }
            def manifesto = project.getExtensions().findByName('manifesto')
        expect:
            manifesto.vendor == 'Jake Gage'
            manifesto.vendor_id == 'com.github.dispader'
            manifesto.url == 'https://github.com/Dispader/manifesto'
    }

    def '(plugins: java, manifesto) adds configuration extensions to manifests'() {
        given:
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.configure(project) {
                manifesto {
                    vendor = 'Jake Gage'
                    vendor_id = 'com.github.dispader'
                    url = 'https://github.com/Dispader/manifesto'
                }
            }
            project.pluginManager.apply 'java'
            def attributes = project.tasks.jar.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Specification-Vendor'     | 'Jake Gage'
            'Implementation-Vendor'    | 'Jake Gage'
            'Implementation-Vendor-Id' | 'com.github.dispader'
            'Implementation-URL'       | 'https://github.com/Dispader/manifesto'
    }

    def '(plugins: war, manifesto) adds configuration extensions to manifests'() {
        given:
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.configure(project) {
                manifesto {
                    vendor = 'Jake Gage'
                    vendor_id = 'com.github.dispader'
                    url = 'https://github.com/Dispader/manifesto'
                }
            }
            project.pluginManager.apply 'war'
            def attributes = project.tasks.war.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Specification-Vendor'     | 'Jake Gage'
            'Implementation-Vendor'    | 'Jake Gage'
            'Implementation-Vendor-Id' | 'com.github.dispader'
            'Implementation-URL'       | 'https://github.com/Dispader/manifesto'
    }

    def '(plugins: java, manifesto) defaults Implementation-Vendor-Id from the project group'() {
        given:
            project.group = 'com.github.dispader'
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.pluginManager.apply 'java'
            def attributes = project.tasks.jar.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Implementation-Vendor-Id' | 'com.github.dispader'
    }

    def '(plugins: java, manifesto) overrides Implementation-Vendor-Id with configuration'() {
        given:
            project.group = 'org.example.user'
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.configure(project) {
                manifesto {
                    vendor_id = 'com.github.dispader'
                }
            }
            project.pluginManager.apply 'java'
            def attributes = project.tasks.jar.manifest.attributes
        expect:
            attributes[attribute] ==~ pattern
        where:
            attribute                  | pattern
            'Implementation-Vendor-Id' | 'com.github.dispader'
    }

    def '(plugins: java, manifesto) defaults Versions from project version'() {
        setup:
            Version.metaClass.static.getSpecification = { '' }
            Version.metaClass.static.getImplementation = { '' }
            project.version = '1.2.3'
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.pluginManager.apply 'java'
        when:
            def attributes = project.tasks.jar.manifest.attributes
        then:
            attributes[attribute] ==~ pattern
        where:
            attribute                | pattern
            'Specification-Version'  | '1.2.3'
            'Implementation-Version' | '1.2.3'
    }

    def '(plugins: java, manifesto) sets project version'() {
        setup:
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.pluginManager.apply 'java'
        expect:
            project.version ==~ /\d+\.\d+\.\d(-.*)?/
    }

}
