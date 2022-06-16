package models

import java.util.Date

data class Client(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val address: String,
  val phone: String,
  val birthDay: Date,
  val login: String,
  val password: String,
  val roleName: String
){
  fun print() {
    println(
      "firstName: ${firstName}, " +
        "lastName: ${lastName}, " +
        "address: ${address}, " +
        "phone: ${phone}, " +
        "birthDay: ${birthDay}, " +
        "roleName: $roleName"
    )
  }
}
