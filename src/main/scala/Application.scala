import java.sql.Timestamp
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object Application extends App {

  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  extractAndLoad()

  private def extractAndLoad(): Unit = {
    spark.read
      .option("header", "true")
      .schema(Schemas.schema)
      .csv(s"${Paths.events}")
      .groupBy("eventDate").agg(count("*"), sum("value"))
      .withColumnRenamed("count(1)", "eventCount")
      .withColumnRenamed("sum(value)", "eventSum")
      .repartition(1)
      .write
      .format("csv")
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .save(s"${Paths.eventsAgg}")
  }

}
