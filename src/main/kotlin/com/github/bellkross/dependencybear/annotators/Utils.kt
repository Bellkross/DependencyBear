package com.github.bellkross.dependencybear.annotators

fun findRanges(text: String, keywords: List<String>): List<IntRange> {
    return keywords.map { keyword -> findRanges(text, keyword) }.flatten()
}

fun findRanges(text: String, keyword: String): List<IntRange> {
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