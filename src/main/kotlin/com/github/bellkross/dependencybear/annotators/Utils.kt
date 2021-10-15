package com.github.bellkross.dependencybear.annotators

import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiComment
import com.intellij.psi.impl.source.tree.PsiCommentImpl
import com.intellij.util.containers.stream
import java.util.function.Function
import java.util.stream.Collectors

internal fun getModulesNames(project: Project): List<String> {
    return ModuleManager.getInstance(project).modules.map { it.name }
}

/**
 * Finds names of dependencies for each module
 * @return [Map] with key as module name and value as a list of module's dependencies name
 */
internal fun getModuleNameToDependenciesNames(project: Project) : Map<String, List<String>> =
    ModuleManager.getInstance(project).modules.associateBy(
        Module::getName,
        ::getDependenciesNames
    )

internal fun getDependenciesNames(module: Module): List<String> {
    return ModuleRootManager.getInstance(module).orderEntries().classes().roots.map(VirtualFile::getName)
}

/**
 * Finds occurrences of [keywords] in the comment component
 * @return List of [TextRange]s, where keywords occur
 */
internal fun PsiComment.findRanges(keywords: List<String>): List<TextRange> {
    return text.findRanges(keywords).map { range ->
        TextRange(range.first, range.last + 1).shiftRight(textRange.startOffset)
    }
}

/**
 * Finds occurrences of [keywords] in the string
 * @return List of ranges, where keywords occur
 */
internal fun String.findRanges(keywords: List<String>): List<IntRange> {
    return keywords.map(::findRanges).flatten()
}

/**
 * Finds occurrences of [keyword] in the string
 * @return List of ranges, where keyword occur. [IntRange.start] and [IntRange.last] are inclusive
 */
internal fun String.findRanges(keyword: String): List<IntRange> {
    val ranges: MutableList<IntRange> = mutableListOf()
    var from: Int = this.indexOf(keyword, 0, true)
    while (from != -1) {
        ranges.add(from until from + keyword.length)
        from = this.indexOf(keyword, from + keyword.length, true)
    }
    return ranges
}