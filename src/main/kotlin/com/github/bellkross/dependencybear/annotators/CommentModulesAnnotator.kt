package com.github.bellkross.dependencybear.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

/**
 * Highlights any occurrence of the names of the modules of the current project,
 * if they occur in a comment.
 */
class CommentModulesAnnotator: Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is PsiComment) {
            return
        }
        commentElement.findRanges(getModulesNames(commentElement.project)).forEach { moduleWordRange ->
            val message = Tooltips.PROJECT_MODULE
            holder.newAnnotation(HighlightSeverity.INFORMATION, message)
                .range(moduleWordRange)
                .textAttributes(DefaultLanguageHighlighterColors.METADATA)
                .tooltip(message)
                .create()
        }
    }

}