package com.github.dispader.manifesto

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar

class ManifestoPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.extensions.create('manifesto', ManifestoPluginExtension, project)

        def version = new Version()
        project.version = version.version

        project.plugins.whenPluginAdded { plugin ->
            project.tasks.withType(Jar).each {
                it.manifest.with {
                    attributes << [
                        'Specification-Title'     : project.name,
                        'Specification-Vendor'    : project.manifesto.vendor,
                        'Specification-Version'   : version.specification,
                        'Implementation-Title'    : project.name,
                        'Implementation-Vendor'   : project.manifesto.vendor,
                        'Implementation-Vendor-Id': project.manifesto.vendor_id,
                        'Implementation-URL'      : project.manifesto.url,
                        'Implementation-Version'  : version.implementation,
                        'Implementation-Timestamp': new Date()
                    ]
                }
            }
        }

    }
}
