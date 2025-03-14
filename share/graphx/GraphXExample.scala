import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.sql.SparkSession

object GraphXExampleHDFS {
  def main(args: Array[String]): Unit = {
    // Create the Spark session; the master URL is supplied via spark-submit.
    val spark = SparkSession.builder
      .appName("GraphXExampleHDFS")
      .getOrCreate()
    val sc = spark.sparkContext

    // Define HDFS path for the input edge file.
    val edgesPath = "hdfs://master:9000/edge.txt"      // Adjust this HDFS path as needed.
    // Define the output directory on HDFS
    val outputPath = "hdfs://master:9000/pagerank_output" // Make sure this directory doesn't exist before running

    // Load the graph from the HDFS edge list file.
    val graph = GraphLoader.edgeListFile(sc, edgesPath)

    // Run the PageRank algorithm on the graph.
    val ranks = graph.staticPageRank(10).vertices

    // Dump the results to HDFS.
    ranks.saveAsTextFile(outputPath)

    // Optionally, print some of the results for verification.
    println("PageRank Results (sample):")
    ranks.take(10).foreach { case (id, rank) =>
      println(s"Vertex $id: $rank")
    }
    
    spark.stop()
  }
}
