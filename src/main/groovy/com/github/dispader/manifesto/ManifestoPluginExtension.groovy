package com.github.dispader.manifesto

import org.gradle.api.Project

class ManifestoPluginExtension {

    String vendor = ""
    String vendor_id = ""
    String url = ""

    ManifestoPluginExtension(Project project) {
        this.vendor_id = project.group
    }

}
