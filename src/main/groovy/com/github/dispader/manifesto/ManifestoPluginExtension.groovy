package com.github.dispader.manifesto

import org.gradle.api.Project

class ManifestoPluginExtension {

    String vendor, vendor_id, url

    ManifestoPluginExtension(Project project) {
        this.vendor_id = project.group
    }

}
