package models

import java.util.Date

data class Car(
  val id: Int,
  val color: String,
  val factoryName: String,
  val price: Float,
  val modelName: String,
  val yearOfCreation: Date
)
