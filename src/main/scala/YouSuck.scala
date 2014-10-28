import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

import org.json4s._
import org.json4s.jackson.JsonMethods._

object YouSuck {
	def main(args: Array[String]) {
		val workDir = args(0)
		val conf = new SparkConf().setMaster("local").setAppName("Streaming")
		val ssc = new StreamingContext(conf, Seconds(20))
		ssc.checkpoint("file:/tmp/yousuck-checkpoint")
		val lines = ssc.textFileStream(workDir)

		val ratings = lines.map(line => (parse(line) \\ "slide_title" -> parse(line) \\ "rating"))
		val ratingsCount = ratings.map(rating => (rating, 1))

		val ratingsTotals = ratingsCount.reduceByKey((a, b) => (a + b))

		val runningCounts = ratingsTotals.updateStateByKey(updateRunningCounts)
		runningCounts.print()
		runningCounts.saveAsTextFiles("file:/tmp/streaming-output")

		ssc.start()
		ssc.awaitTermination()
	}

	def updateRunningCounts(values: Seq[Int], counts: Option[Int]): Option[Int] = {
		val currentCount = values.sum
		val previousCount = counts.getOrElse(0)
		Some(currentCount+previousCount)
	}
}
