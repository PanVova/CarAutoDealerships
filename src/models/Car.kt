package models

import java.util.Date

data class Car(
  val id: Int,
  val color: String,
  val factoryName: String,
  val price: Float,
  val modelName: String,
  val yearOfCreation: Date
) {
  fun print() {
    println(
      "name: ${modelName}, " +
        "color: ${color}, " +
        "factory: ${factoryName}, " +
        "price: ${price}, " +
        "year of creation: $yearOfCreation"
    )
  }
}
