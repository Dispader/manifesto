package com.github.dispader.manifesto

import spock.lang.Shared
import spock.lang.Specification
import org.gradle.api.*
import org.gradle.testfixtures.*

class ManifestoPluginSpec extends Specification {

    private Project project = ProjectBuilder.builder().build()

    @Shared private def gitPopulatedKeys =
        [ 'Specification-Title', 'Specification-Version',
          'Implementation-Title', 'Implementation-Version', 'Implementation-Timestamp' ]

    def 'adds a "diagnostics" task'() {
        given:
            project.pluginManager.apply 'com.github.dispader.manifesto'
        expect:
            project.tasks.diagnostics instanceof Task
    }

    def 'without plugin, JAr manifest contains a version'() {
        given:
            project.pluginManager.apply 'java'
        expect:
            project.tasks.jar.manifest.attributes.containsKey('Manifest-Version')
    }

    def 'adds "Implementation-Timestamp" to JAr when manifesto loaded before java'() {
        given:
            project.pluginManager.apply 'com.github.dispader.manifesto'
            project.pluginManager.apply 'java'
        expect:
            project.tasks.jar.manifest.attributes.containsKey('Manifest-Version')
            project.tasks.jar.manifest.attributes.containsKey(key)
        where:
            key << gitPopulatedKeys
    }

    def 'adds "Implementation-Timestamp" to JAr when manifesto loaded after java'() {
        given:
            project.pluginManager.apply 'java'
            project.pluginManager.apply 'com.github.dispader.manifesto'
        expect:
            project.tasks.jar.manifest.attributes.containsKey('Manifest-Version')
            project.tasks.jar.manifest.attributes.containsKey(key)
        where:
            key << gitPopulatedKeys
    }

    def 'adds "Implementation-Timestamp" to JAr when war plugin loaded'() {
        given:
            project.pluginManager.apply 'war'
            project.pluginManager.apply 'com.github.dispader.manifesto'
        expect:
            project.tasks.jar.manifest.attributes.containsKey('Manifest-Version')
            project.tasks.jar.manifest.attributes.containsKey(key)
            project.tasks.war.manifest.attributes.containsKey('Manifest-Version')
            project.tasks.war.manifest.attributes.containsKey(key)
        where:
            key << gitPopulatedKeys
    }

}
