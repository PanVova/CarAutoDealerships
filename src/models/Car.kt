package models

import java.util.Date

data class Car(
  val id: Int,
  val color: String,
  val factoryId: Int,
  val priceListId: Int,
  val modelName: String,
  val yearOfCreation: Date
)
