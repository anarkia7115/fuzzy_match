// Source: src/test/scala/com/mycompany/scalcium/umls/UmlsTaggerTest.scala
package com.gcbi.shawn.fuzzy_match
//import org.hamcrest.SelfDescribing
import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.FileSystems
import org.junit.Assert

class UmlsTaggerTest {

  //@Test
  def testSortWords(): Unit = {
    val s = "heart attack and diabetes"
    val tagger = new UmlsTagger()
    Assert.assertEquals("and attack diabetes heart", tagger.sortWords(s))
  }
  
  //@Test
  def testStemWords(): Unit = {
    val s = "and attack diabetes heart"
    val tagger = new UmlsTagger()
    Assert.assertEquals("attack diabetes heart", tagger.stemWords(s))
  }

  //@Test
  def testBuild(): Unit = {
    //val input = new File("/home/sujit/Projects/med_data/cuistr1.csv")
    //val output = new File("/home/sujit/Projects/med_data/umlsindex")
    val input = new File("./data/cuistr.csv")
    //val output = new Path("./data/umlsindex")
    val output = FileSystems.getDefault().getPath("./data/umlsindex") 
    val tagger = new UmlsTagger()
    tagger.buildIndex(input, output)
  }
  
  @Test
  def testMapSingleConcept(): Unit = {
    //val luceneDir = new Path("/home/sujit/Projects/med_data/umlsindex")
    Console.println("Single Concept")
    val luceneDir = FileSystems.getDefault().getPath("./data/umlsindex") 
    val tagger = new UmlsTagger()
    val strs = List("Lung Cancer", "Heart Attack", "Diabetes")
    strs.foreach(str => {
      val concepts = tagger.annotateConcepts(str, luceneDir)
      Console.println("rawQuery: " + str)
      tagger.printConcepts(concepts)
      Assert.assertEquals(1, concepts.size)
      Assert.assertEquals(100.0D, concepts.head._1, 0.1D)
    })
  }

  //@Test
  def testMapMultipleConcepts(): Unit = {
    //val luceneDir = new Path("/home/sujit/Projects/med_data/umlsindex")
    //val luceneDir = new Path("./data/umlsindex")
    val luceneDir = FileSystems.getDefault().getPath("./data/umlsindex") 
    val tagger = new UmlsTagger()
    val strs = List(
        "Heart Attack and diabetes",
        "carcinoma (small-cell) of lung",
        "asthma side effects")
    strs.foreach(str => {
      val concepts = tagger.annotateConcepts(str, luceneDir)
      Console.println("Query: " + str)
      tagger.printConcepts(concepts)
    })
  }
}
