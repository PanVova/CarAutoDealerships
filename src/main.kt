fun main() {
  val dbInstance = MySQLDB()
  dbInstance.apply {
    openConnection()
    admin()
    closeConnection()
  }
}