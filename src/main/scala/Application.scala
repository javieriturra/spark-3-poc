import org.apache.spark.sql.{SaveMode, SparkSession}

object Application extends App {

  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  extract("2020-08-12")

  private def extract(date: String) = {
    spark.read
      .option("header", "true")
      .schema(Schemas.schema)
      .csv(s"${Paths.events}/")
      .write
      .format("csv")
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .save(s"${Paths.count}/$date")
  }

}
