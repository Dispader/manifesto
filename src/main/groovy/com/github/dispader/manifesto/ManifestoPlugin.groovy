package com.github.dispader.manifesto

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.War
import org.gradle.jvm.tasks.Jar

class ManifestoPlugin implements Plugin<Project> {

    boolean jar_configured = false
    boolean war_configured = false

    void apply(Project project) {

        project.extensions.create('manifesto', ManifestoPluginExtension, project)

        project.plugins.whenPluginAdded { plugin ->
            project.tasks.findAll { ( it instanceof Jar || it instanceof War ) }.each {
                it.manifest.with {

                    if ( ( it instanceof Jar ) && jar_configured ) {
                        project.logger.warn "warning: Jar Manifest already configured."
                        return
                    }
                    if ( ( it instanceof War ) && war_configured ) {
                        project.logger.warn "warning: War Manifest already configured."
                        return
                    }

                    def version = new Version()
                    if ( !version ) {
                        project.logger.warn "warning: ${Version.warningText}"
                        return
                    }

                    if ( !project.version || project.version == 'unspecified' ) {
                        project.version = version.version
                    } else {
                        project.logger.warn "warning: Manifest version not set."
                    }

                    attributes('Specification-Title': project.name)

                    if ( project.manifesto?.vendor ) {
                        attributes('Specification-Vendor': "${project.manifesto.vendor}")
                    } else {
                        project.logger.warn "warning: Manifest Specification-Vendor not set."
                    }

                    if ( version.specification ) {
                        attributes('Specification-Version': version.specification)
                    } else if ( project.version ) {
                        attributes('Specification-Version': "${project.version}")
                    } else {
                        project.logger.warn "warning: Manifest Specification-Version not set."
                    }

                    attributes('Implementation-Title': project.name)

                    if ( project.manifesto?.vendor ) {
                        attributes('Implementation-Vendor': "${project.manifesto.vendor}")
                    } else {
                        project.logger.warn "warning: Manifest Implementation-Vendor not set."
                    }

                    if ( project.manifesto?.vendor_id ) {
                        attributes('Implementation-Vendor-Id': "${project.manifesto.vendor_id}")
                    } else {
                        project.logger.warn "warning: Manifest Implementation-Vendor-Id not set."
                    }

                    if ( project.manifesto?.url ) {
                        attributes('Implementation-URL': "${project.manifesto.url}")
                    } else {
                        project.logger.warn "warning: Manifest Implementation-URL not set."
                    }

                    if ( version.implementation ) {
                        attributes('Implementation-Version': version.implementation)
                    } else if ( project.version ) {
                        attributes('Implementation-Version': "${project.version}")
                    } else {
                        project.logger.warn "warning: Manifest Implementation-Version not set."
                    }

                    attributes('Implementation-Timestamp': new Date())

                    if ( it instanceof Jar ) { jar_configured = true }
                    if ( it instanceof War ) { war_configured = true }
                }
            }
        }

    }
}
