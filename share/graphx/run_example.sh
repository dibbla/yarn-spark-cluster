hdfs dfs -rm /edge.txt
hdfs dfs -rm /vertices.txt
hdfs dfs -put edge.txt /
hdfs dfs -put vertices.txt /

hdfs dfs -rm -r /pagerank_output

time spark-submit --master yarn \
    --deploy-mode cluster \
    --name "Graph" \
    --executor-memory 1g \
    --class GraphXExampleHDFS \
    GraphXExample.jar

hdfs dfs -get /pagerank_output /opt/share/graphx/pagerank_output