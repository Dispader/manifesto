package com.github.dispader.manifesto

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.War
import org.gradle.jvm.tasks.Jar

class ManifestoPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.extensions.create('manifesto', ManifestoPluginExtension)

        project.task('diagnostics') << {
            println "Specification-Title: ${project.name}"
            println "Specification-Version: ${Version.specification}"
            if ( project.manifesto.vendor ) {
                println "Specification-Vendor: ${project.manifesto.vendor}"
            }
            println "Implementation-Title: ${project.name}"
            if ( project.manifesto.vendor_id ) {
                println "Implementation-Vendor-Id: ${project.manifesto.vendor_id}"
            }
            println "Implementation-Version: ${Version.implementation}"
            if ( project.manifesto.vendor ) {
                println "Implementation-Vendor: ${project.manifesto.vendor}"
            }
            if ( project.manifesto.url ) {
                println "Implementation-URL: ${project.manifesto.url}"
            }
            println "Implementation-Timestamp: ${new Date()}"
        }

        project.plugins.whenPluginAdded { plugin ->
            project.tasks.find { it instanceof Jar }.each {
                it.manifest.with {
                    attributes('Specification-Title': project.name)
                    attributes('Specification-Version': Version.specification)
                    attributes('Implementation-Title': project.name)
                    attributes('Implementation-Version': Version.implementation)
                    attributes('Implementation-Timestamp': new Date())
                }
            }
            project.tasks.find { it instanceof War }.each {
                it.manifest.with {
                    attributes('Specification-Title': project.name)
                    attributes('Specification-Version': Version.specification)
                    attributes('Implementation-Title': project.name)
                    attributes('Implementation-Version': Version.implementation)
                    attributes('Implementation-Timestamp': new Date())
                }
            }
        }

    }

}
