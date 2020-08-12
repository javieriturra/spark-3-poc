import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType, TimestampType}

object Schemas {

  // id,eventDate,value,creationDate

  val schema = StructType(Seq(
    StructField("id", StringType, nullable = false),
    StructField("eventDate", TimestampType, nullable = false),
    StructField("value", IntegerType, nullable = false),
    StructField("creationDate", TimestampType, nullable = true),
  ))

}
