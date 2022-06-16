package models

data class UserRole(
  val id: Int,
  val name: String
){
  fun print(){
    println("userRole: $name")
  }
}
