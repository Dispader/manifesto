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

                    def version = new Version()
                    if ( !version ) {
                        project.logger.warn "warning: ${Version.warningText}"
                        return
                    }

                    if ( !project.version || project.version == 'unspecified' ) {
                        project.version = version.version
                    }

                    attributes('Specification-Title': project.name)
                    if ( version.specification ) {
                        attributes('Specification-Version': version.specification)
                    } else if ( project.version ) {
                        attributes('Specification-Version': "${project.version}")
                    }
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
                    if ( version.implementation ) {
                        attributes('Implementation-Version': version.implementation)
                    } else if ( project.version ) {
                        attributes('Implementation-Version': "${project.version}")
                    }
                    attributes('Implementation-Timestamp': new Date())
                }
            }
        }

    }
}
