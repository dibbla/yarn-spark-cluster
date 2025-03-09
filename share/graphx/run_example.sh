spark-submit --master yarn \
    --deploy-mode cluster \
    --name "Graph" \
    --executor-memory 1g \
    --class GraphXExampleHDFS \
    GraphXExample.jar