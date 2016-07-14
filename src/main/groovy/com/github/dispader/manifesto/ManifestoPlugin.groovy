package com.github.dispader.manifesto

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.War
import org.gradle.jvm.tasks.Jar

class ManifestoPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.extensions.create('manifesto', ManifestoPluginExtension, project)

        project.plugins.whenPluginAdded { plugin ->
            project.tasks.findAll { ( it instanceof Jar || it instanceof War ) }.each {
                it.manifest.with {
                    attributes('Specification-Title': project.name)
                    attributes('Specification-Version': Version.specification)
                    if ( project?.manifesto?.vendor ) {
                        attributes('Specification-Vendor': "${project.manifesto.vendor}")
                    }
                    if ( project?.manifesto?.vendor ) {
                        attributes('Implementation-Vendor': "${project.manifesto.vendor}")
                    }
                    if ( project?.manifesto?.vendor_id ) {
                        attributes('Implementation-Vendor-Id': "${project.manifesto.vendor_id}")
                    }
                    if ( project?.manifesto?.url ) {
                        attributes('Implementation-URL': "${project.manifesto.url}")
                    }
                    attributes('Implementation-Title': project.name)
                    attributes('Implementation-Version': Version.implementation)
                    attributes('Implementation-Timestamp': new Date())
                }
            }
        }

    }
}
