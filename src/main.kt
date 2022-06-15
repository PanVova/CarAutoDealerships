fun main() {
  val dbInstance = MySQLDB()
  dbInstance.apply {
    openConnection()
    testMethods()
    closeConnection()
  }
}