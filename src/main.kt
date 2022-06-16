fun main() {
  val dbInstance = MySQLDB()
  dbInstance.apply {
    openConnection()
    loginOrRegister()
    closeConnection()
  }
}