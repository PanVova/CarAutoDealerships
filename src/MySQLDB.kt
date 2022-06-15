import users.Admin
import users.AdminImpl
import users.Customer
import users.CustomerImpl
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.SQLException

class MySQLDB {

  private lateinit var connection: Connection

  private lateinit var customer: Customer
  private lateinit var admin: Admin

  fun openConnection() {
    try {
      connection = DriverManager.getConnection(url, user, password)
      customer = CustomerImpl(connection)
      admin = AdminImpl(connection)
    } catch (sqlEx: SQLException) {
      println("Can't connect to mysql database")
    }
  }

  fun closeConnection() {
    try {
      connection.close()
      println("Closed successfully")
    } catch (sqlEx: SQLException) {
      println("Closed with error: $sqlEx")
    }
  }

  fun testMethods() {
    customer.showCars(1)
    admin.getClients()
    admin.getRoles()
    admin.getFactories()
    admin.addFactory("Porsche")
    admin.updateFactory("Porsche", "k2")
    admin.getPricesList()
    admin.addPriceList(100F)
    admin.deleteCar("V5")
    admin.addCar(
      color = "Blue", factoryName = "Mazda", price = 20000F, modelName = "V15",
      Date.valueOf("1993-06-10")
    )
    admin.makeOrder("V15", clientLogin = "login", price = 20000F, discount = 1000F)
    admin.showTransactionsOfUser("login")
  }

  companion object {
    private const val url = "jdbc:mysql://localhost:3306"
    private const val user = "root"
    private const val password = "1234"
  }
}