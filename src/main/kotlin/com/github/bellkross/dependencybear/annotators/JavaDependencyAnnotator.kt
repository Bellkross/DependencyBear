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


class JavaDependencyAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PsiCommentImpl) {
            return
        }
        val modulesNames = ModuleManager.getInstance(element.project).modules.map { it.name }
        val comment: PsiCommentImpl = element
        if (!modulesNames.any { comment.text.contains(it) }) {
            return
        }
        val modulesTextRanges = modulesNames.map { moduleName -> findRanges(comment.text, moduleName) }
            .flatten().map { range ->
                TextRange(range.first, range.last + 1).shiftRight(comment.textRange.startOffset)
            }
        modulesTextRanges.forEach { simpleWordRange ->
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(simpleWordRange)
                .textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()
        }
    }

    private fun findRanges(text: String, keyword: String): List<IntRange> {
        val ranges: MutableList<IntRange> = mutableListOf()
        var from: Int = text.indexOf(keyword, 0, true)
        while (from != -1) {
            ranges.add(
                from until from + keyword.length
            )
            from = text.indexOf(keyword, from + keyword.length, true)
        }
        return ranges
    }

}
