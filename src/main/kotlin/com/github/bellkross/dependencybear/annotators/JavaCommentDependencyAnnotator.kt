package com.github.bellkross.dependencybear.annotators

// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiCommentImpl


class JavaCommentDependencyAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PsiCommentImpl) {
            return
        }
        val comment: PsiCommentImpl = element
        val modulesNames = ModuleManager.getInstance(comment.project).modules.map { it.name }
        if (!modulesNames.any { comment.text.contains(it) }) {
            return
        }
        val modulesTextRanges = findRanges(comment.text, modulesNames).map { range ->
            TextRange(range.first, range.last + 1).shiftRight(comment.textRange.startOffset)
        }
        modulesTextRanges.forEach { simpleWordRange ->
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(simpleWordRange)
                .textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()
        }
    }

}
