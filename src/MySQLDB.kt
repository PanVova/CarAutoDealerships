import models.Role
import users.Admin
import users.AdminImpl
import users.Customer
import users.CustomerImpl
import users.User
import users.UserImpl
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.system.exitProcess

class MySQLDB {

  private lateinit var connection: Connection

  private lateinit var customer: Customer
  private lateinit var admin: Admin
  private lateinit var user: User

  fun openConnection() {
    try {
      connection = DriverManager.getConnection(url, login, password)
      customer = CustomerImpl(connection)
      admin = AdminImpl(connection)
      user = UserImpl(connection)
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

  private fun customer() {
    while (true) {
      println(
        """
----------------------------
Hello, customer. You have a few options today:
 1.show cars
 2.show my transactions
 3.close program
----------------------------"""
      )
      when (readLine() ?: "") {
        "1" -> {
          println(
            "Now from what id number you want to see available cars. If it doesn't matter just enter 0"
          )
          val from = readLine() ?: ""
          if (from.toIntOrNull() != null) {
            customer.showCars(from.toInt())
          } else {
            customer.showCars()
          }
        }
        "2" -> customer.showTransactionsOfUser(user.userLogin)
        "3" -> {
          closeConnection()
          exitProcess(0)
        }
        else -> println("Wrong input")
      }
    }
  }

  fun loginOrRegister() {
    var result: Pair<Int, Role>
    while (true) {
      println("Welcome:\n 1.Login\n 2.Register\n 3.exit program")
      when (readLine() ?: "") {
        "1" -> {
          println("Enter login:")
          val login = readLine() ?: ""

          println("Enter password:")
          val password = readLine() ?: ""

          result = user.login(login, password)
          if (result.first == 0) {
            break
          }
        }
        "2" -> {
          println("Enter firstName:")
          val firstName = readLine() ?: ""

          println("Enter lastName:")
          val lastName = readLine() ?: ""

          println("Enter address:")
          val address = readLine() ?: ""

          println("Enter phone number:")
          val phone = readLine() ?: ""

          println("Enter your birthday format (yyyy-mm-dd) :")
          val dateInput = readLine() ?: "1990-01-02"
          val birthDay = Date.valueOf(dateInput)

          println("Enter login:")
          val login = readLine() ?: ""

          println("Enter password:")
          val password = readLine() ?: ""

          result =
            user.register(firstName, lastName, address, phone, birthDay, login, password)
          if (result.first == 0) {
            break
          }
        }
        "3" -> exitProcess(0)
        else -> println("Wrong input")
      }
    }
    if (result.second == Role.CUSTOMER) {
      customer()
    } else if (result.second == Role.ADMIN) {
      admin()
    }
  }

  private fun admin() {
    while (true) {
      println(
        """
------------------------------------
 Hello. Welcome home admin. You have a few options today:
 1.get information about clients
 2.get all available roles
 3.get information about all factories
 4.add factory
 5.edit factory
 6.get prices list
 7.add price list
 8.add car
 9.delete car
 10.make an order
 11.show cars
 12.close program
------------------------------------"""
      )
      print("Your option: ")
      val option = readLine() ?: ""
      when (option.toInt()) {
        1 -> admin.getClients()
        2 -> admin.getRoles()
        3 -> admin.getFactories()
        4 -> addFactory()
        5 -> updateFactory()
        6 -> admin.getPricesList()
        7 -> addPriceList()
        8 -> addCar()
        9 -> deleteCar()
        10 -> makeOrder()
        11 -> customer.showCars()
        12 -> exitProcess(0)
        else -> println("Wrong input")
      }
    }
  }

  private fun makeOrder() {
    println("Enter car name")
    val carName = readLine() ?: ""
    if (carName == "") println("Wrong input")
    else {
      println("Enter a client login")
      val clientLogin = readLine() ?: ""
      if (clientLogin == "") println("Wrong input")
      else {
        println("Enter price from price list")
        val price = readLine() ?: ""
        if (price.toFloatOrNull() == null || price.toFloat() < 0) println("Wrong input")
        else {
          println("Enter discount if any")
          val discount = readLine() ?: ""
          if (discount.toFloatOrNull() == null || discount.toFloat() < 0) {
            println("Wrong input for discount we will count it as 0")
            admin.makeOrder(carName, clientLogin, price.toFloat())
          } else {
            admin.makeOrder(carName, clientLogin, price.toFloat(), discount.toFloat())
          }
        }
      }
    }
  }

  private fun deleteCar() {
    println("Enter a car that you want to delete")
    val carName = readLine() ?: ""
    if (carName == "") println("Wrong input")
    else admin.deleteCar(carName)
  }

  private fun addCar() {
    println("Enter car name")
    val carName = readLine() ?: ""
    if (carName == "") println("Wrong input")
    else {
      println("Enter color")
      val color = readLine() ?: ""
      if (color == "") println("Wrong input")
      else {
        println("Enter price from price list")
        val price = readLine() ?: ""
        if (price.toFloatOrNull() == null || price.toFloat() < 0) println("Wrong input")
        else {
          println("Enter factory name")
          val factoryName = readLine() ?: ""
          if (factoryName == "") println("Wrong input")
          else {
            println("Enter date for car: Format YYYY-mm-dd")
            val date = readLine() ?: ""
            if (date != "") {
              admin.addCar(
                color = color, factoryName = factoryName, price = price.toFloat(),
                modelName = carName,
                Date.valueOf(date)
              )
            } else println("Wrong input")
          }
        }
      }
    }
  }

  private fun addPriceList() {
    println("Enter new price list")
    val price = readLine() ?: ""
    if (price.toFloatOrNull() != null && price.toFloat() > 0F) println("Wrong input")
    else admin.addPriceList(price.toFloat())
  }

  private fun updateFactory() {
    println("Enter an old factory name")
    val oldFactoryName = readLine() ?: ""
    if (oldFactoryName == "") println("Wrong input")
    else {
      println("Enter a new factory name")
      val updatedFactoryName = readLine() ?: ""
      if (updatedFactoryName == "") println("Wrong input")
      admin.updateFactory(oldFactoryName, updatedFactoryName)
    }
  }

  private fun addFactory() {
    println("Enter a new factory name")
    val input2 = readLine() ?: ""
    if (input2 == "") println("Wrong input")
    else admin.addFactory(input2)
  }

  companion object {
    private const val url = "jdbc:mysql://localhost:3306"
    private const val login = "root"
    private const val password = "1234"
  }
}