export PATH=/usr/local/scala/bin:$PATH

scalac -classpath "$SPARK_HOME/jars/*" GraphXExample.scala
jar cf GraphXExample.jar *.class
