fun main() {
  val dbInstance = MySQLDB()
  dbInstance.apply {
    openConnection()
    showCars()
    closeConnection()
  }
}