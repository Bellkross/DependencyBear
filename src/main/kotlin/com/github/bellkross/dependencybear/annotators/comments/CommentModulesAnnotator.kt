package com.github.bellkross.dependencybear.annotators.comments

import com.github.bellkross.dependencybear.annotators.annotateModules
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
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
        annotateModules(commentElement, holder)
    }

}