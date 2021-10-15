package com.github.bellkross.dependencybear.annotators.literals

import com.github.bellkross.dependencybear.annotators.annotateDependencies
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtTokens.REGULAR_STRING_PART

class KotlinLiteralsDependencyAnnotator: Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is LeafPsiElement || commentElement.elementType != REGULAR_STRING_PART) {
            return
        }
        annotateDependencies(commentElement, holder)
    }

}