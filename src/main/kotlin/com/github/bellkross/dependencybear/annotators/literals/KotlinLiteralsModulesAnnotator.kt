package com.github.bellkross.dependencybear.annotators.literals

import com.github.bellkross.dependencybear.annotators.annotateModules
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtTokens

/**
 * Highlights any occurrence of the modules of the project,
 * if they occur in a [String] literal.
 */
class KotlinLiteralsModulesAnnotator: Annotator {

    override fun annotate(commentElement: PsiElement, holder: AnnotationHolder) {
        if (commentElement !is LeafPsiElement || commentElement.elementType != KtTokens.REGULAR_STRING_PART) {
            return
        }
        annotateModules(commentElement, holder)
    }

}