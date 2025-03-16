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
    val outputPath = "hdfs://master:9000/connected_components_output" // Make sure this directory doesn't exist before running

    // Load the graph from the HDFS edge list file.
    val graph = GraphLoader.edgeListFile(sc, edgesPath)

    // Find the connected components
    val cc = graph.connectedComponents().vertices

    // Dump the results to HDFS.
    cc.saveAsTextFile(outputPath)

    // Optionally, print some of the results for verification.
    println("Connected Components Results (sample):")
    cc.take(10).foreach { case (id, cc) =>
      println(s"Vertex $id: $cc")
    }
    
    spark.stop()
  }
}
