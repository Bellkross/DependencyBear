package com.github.bellkross.dependencybear.annotators

// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

/**
 * Highlights any occurrence of the names of the dependencies of the current module,
 * if they occur in a comment.
 */
class CommentDependencyAnnotator : Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is PsiComment) {
            return
        }
        val moduleNameToDependenciesNames = getModuleNameToDependenciesNames(commentElement.project)
        moduleNameToDependenciesNames.forEach { (moduleName, dependencyNames) ->
            commentElement.findRanges(dependencyNames).forEach { dependencyWordRange ->
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(dependencyWordRange)
                    .tooltip(String.format(Tooltips.DEPENDENCY, moduleName))
                    .textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()
            }
        }
    }

}
