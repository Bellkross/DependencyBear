package com.github.bellkross.dependencybear.annotators.literals

import com.github.bellkross.dependencybear.annotators.annotateDependencies
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteral

/**
 * Highlights any occurrence of the names of the dependencies of the current module,
 * if they occur in a [String] literal.
 */
class JavaLiteralsDependenciesAnnotator: Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is PsiLiteral) {
            return
        }
        annotateDependencies(commentElement, holder)
    }

}