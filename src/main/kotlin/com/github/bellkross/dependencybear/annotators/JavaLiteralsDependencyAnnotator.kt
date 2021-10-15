package com.github.bellkross.dependencybear.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteral

class JavaLiteralsDependencyAnnotator: Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is PsiLiteral) {
            return
        }
        annotateDependencies(commentElement, holder)
    }

}