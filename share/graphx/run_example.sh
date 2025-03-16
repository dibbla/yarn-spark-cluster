# Do facebook
cp datasets/facebook.txt edge.txt

hdfs dfs -rm /edge.txt
# hdfs dfs -rm /vertices.txt
hdfs dfs -put edge.txt /
# hdfs dfs -put vertices.txt /

hdfs dfs -rm -r /connected_components_output

time spark-submit --master yarn \
    --deploy-mode cluster \
    --name "Graph" \
    --executor-memory 1g \
    --num-executors 3 \
    --class GraphXExampleHDFS \
    GraphXExample.jar

hdfs dfs -get /connected_components_output /opt/share/graphx/connected_components_output

# Do epinions
cp datasets/epinions.txt edge.txt

hdfs dfs -rm /edge.txt
hdfs dfs -put edge.txt /

hdfs dfs -rm -r /connected_components_output


time spark-submit --master yarn \
    --deploy-mode cluster \
    --name "Graph" \
    --executor-memory 1g \
    --num-executors 3 \
    --class GraphXExampleHDFS \
    GraphXExample.jar

hdfs dfs -get /connected_components_output /opt/share/graphx/connected_components_output

# Do google
cp datasets/google.txt edge.txt
hdfs dfs -rm /edge.txt
hdfs dfs -put edge.txt /

hdfs dfs -rm -r /connected_components_output

time spark-submit --master yarn \
    --deploy-mode cluster \
    --name "Graph" \
    --executor-memory 1g \
    --num-executors 3 \
    --class GraphXExampleHDFS \
    GraphXExample.jar

hdfs dfs -get /connected_components_output /opt/share/graphx/connected_components_output