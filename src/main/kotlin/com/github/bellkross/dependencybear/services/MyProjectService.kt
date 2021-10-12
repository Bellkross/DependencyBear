package com.github.bellkross.dependencybear.services

import com.intellij.openapi.project.Project
import com.github.bellkross.dependencybear.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
