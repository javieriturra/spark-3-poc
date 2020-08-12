import java.sql.Timestamp
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.{SaveMode, SparkSession}

object Application extends App {

  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  Seq("2020-08-11", "2020-08-12").foreach(x => extract(Timestamp.valueOf(s"$x 00:00:00")))

  private def extract(date: Timestamp): Unit = {
    spark.read
      .option("header", "true")
      .schema(Schemas.schema)
      .csv(s"${Paths.events}/")
      .write
      .format("csv")
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .save(s"${Paths.count}/extractionDate=${DateTimeFormatter.BASIC_ISO_DATE.format(date.toLocalDateTime)}")
  }

}
