package com.github.bellkross.dependencybear.annotators.literals

import com.github.bellkross.dependencybear.annotators.annotateModules
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteral

/**
 * Highlights any occurrence of the modules of the project,
 * if they occur in a [String] literal.
 */
class JavaLiteralsModulesAnnotator: Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is PsiLiteral) {
            return
        }
        annotateModules(commentElement, holder)
    }

}