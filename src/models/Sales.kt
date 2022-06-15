package models

import java.util.Date

data class Sales(
  val id: Int,
  val carId: Int,
  val clientId: Int,
  val dateOfSale: Date,
  val price: Float,
  val discount: Float,
  val totalSum: Float
)
