package com.github.bellkross.dependencybear.annotators

// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl
import com.intellij.psi.impl.source.tree.PsiCommentImpl


class CommentDependencyAnnotator : Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is PsiComment) {
            return
        }
        val moduleDependenciesNames = getDependenciesNames(commentElement.project)
        if (!moduleDependenciesNames.any { commentElement.text.contains(it) }) {
            return
        }
        commentElement.findRanges(moduleDependenciesNames).forEach { dependencyWordRange ->
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(dependencyWordRange)
                .textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()
        }
    }

}
