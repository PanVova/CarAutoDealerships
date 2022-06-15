package models

import java.util.Date

data class Clients(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val address: String,
  val phone: String,
  val birthDay: Date,
  val login: String,
  val password: String
)
