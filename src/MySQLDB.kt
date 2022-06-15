import users.Customer
import users.CustomerImpl
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class MySQLDB {

  private lateinit var connection: Connection

  private lateinit var customer: Customer

  fun openConnection() {
    try {
      connection = DriverManager.getConnection(url, user, password)
      customer = CustomerImpl(connection)
    } catch (sqlEx: SQLException) {
      println("Can't connect to mysql database")
    }
  }

  fun closeConnection() {
    println("------------")

    try {
      connection.close()
      println("Finished successfully")
    } catch (sqlEx: SQLException) {
      println("Finished with error: $sqlEx")
    }
  }

  fun showCars() {
    customer.showCars()
  }

  companion object {
    private const val url = "jdbc:mysql://localhost:3306"
    private const val user = "root"
    private const val password = "1234"
  }
}