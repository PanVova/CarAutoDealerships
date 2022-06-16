package models

data class PriceList(
  val id: Int,
  val price: Float
){
  fun print(){
    println("price: $price")
  }
}
