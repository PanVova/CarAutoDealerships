package models

import java.util.Date

data class Sales(
  val id: Int,
  val modelName: String,
  val dateOfSale: Date,
  val price: Float,
  val discount: Float,
  val totalSum: Float
)
