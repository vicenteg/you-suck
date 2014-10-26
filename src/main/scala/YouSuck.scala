import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

object YouSuck {
	def main(args: Array[String]) {
		val conf = new SparkConf().setMaster("local").setAppName("Streaming")
		val ssc = new StreamingContext(conf, Seconds(10))
		val lines = ssc.textFileStream("file:/home/vince/ratings")

		val words = lines.flatMap(_.split(","))
		val pairs = words.map(word => (word, 1))
		val counts = pairs.reduceByKey(_ + _)

		counts.print()
		ssc.start()
		ssc.awaitTermination()
	}
}
