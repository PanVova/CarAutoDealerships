package models

import java.util.Date

data class Sales(
  val id: Int,
  val modelName: String,
  val dateOfSale: Date,
  val price: Float,
  val discount: Float,
  val totalSum: Float
) {
  fun print(){
    println(
      "model name: ${modelName}, " +
        "date of sale: ${dateOfSale}, " +
        "price: ${price}, " +
        "discount: ${discount}, " +
        "totalSum: $totalSum"
    )
  }
}
