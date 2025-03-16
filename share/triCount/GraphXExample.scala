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

    // Define HDFS paths for the input files.
    val edgesPath = "hdfs://master:9000/edge.txt"      // Adjust this HDFS path as needed.
    val verticesPath = "hdfs://master:9000/vertices.txt"  // Adjust this HDFS path as needed.

    // Load the graph from the HDFS edge list file.
    val graph = GraphLoader.edgeListFile(sc, edgesPath)

    // Run the PageRank algorithm on the graph.
    val triCounts = graph.triangleCount().vertices

    // Load the vertices with names (assuming CSV format: "vertexId,name") from HDFS.
    val users = sc.textFile(verticesPath).map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }

    // Join the user names with their PageRank scores.
    val triCountNames = users.join(triCounts).map {
      case (id, (name, tc)) => (name, tc)
    }

    // Print the PageRank results.
    println("TriangleCount Results:")
    triCountNames.collect().foreach { case (name, tc) =>
      println(s"$name: $tc")
    }
    
    spark.stop()
  }
}
