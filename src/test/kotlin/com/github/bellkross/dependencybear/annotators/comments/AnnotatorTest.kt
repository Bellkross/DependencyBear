package com.github.bellkross.dependencybear.annotators.comments

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class AnnotatorTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath() = "src/test/testData/annotators"

    fun testJavaFilesAnnotator() {
        myFixture.configureByFile("AnnotatorTest.java")
        myFixture.checkHighlighting(false, true, false, true)
    }

    fun testKotlinFilesAnnotator() {
        myFixture.configureByFile("AnnotatorTest.kt")
        myFixture.checkHighlighting(false, true, false, true)
    }

}