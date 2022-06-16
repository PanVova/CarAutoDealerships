package models

data class Factory(
  val id: Int,
  val name: String
){
  fun print(){
    println("factory: $name")
  }
}
