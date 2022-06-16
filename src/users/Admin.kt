package users

import models.Client
import models.Factory
import models.PriceList
import models.Sales
import models.UserRole
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet
import java.sql.SQLException

interface Admin {
  fun getClients()
  fun getRoles()

  fun getFactories()
  fun addFactory(factoryName: String)
  fun updateFactory(oldFactoryName: String, updatedFactoryName: String)

  fun getPricesList()
  fun addPriceList(price: Float)


  fun addCar(
    color: String,
    factoryName: String,
    price: Float,
    modelName: String,
    yearOfCreation: Date
  )

  fun deleteCar(modelName: String)

  fun makeOrder(
    carModelName: String,
    clientLogin: String,
    price: Float,
    discount: Float = 0F
  )
}

class AdminImpl(
  private val connection: Connection
) : Admin {

  private lateinit var resultSet: ResultSet
  private lateinit var query: String

  override fun getClients() {
    query = "select id,\n" +
      "       first_name,\n" +
      "       last_name,\n" +
      "       address,\n" +
      "       phone,\n" +
      "       birth_day,\n" +
      "       login,\n" +
      "       password,\n" +
      "       (select name from db.user_role where clients.role_id = user_role.id) as role_name\n" +
      "from db.clients;"

    resultSet = connection.createStatement().executeQuery(query)
    while (resultSet.next()) {
      val client = Client(
        id = resultSet.getInt("id"),
        firstName = resultSet.getString("first_name"),
        lastName = resultSet.getString("last_name"),
        address = resultSet.getString("address"),
        phone = resultSet.getString("phone"),
        birthDay = resultSet.getDate("birth_day"),
        login = resultSet.getString("login"),
        password = resultSet.getString("password"),
        roleName = resultSet.getString("role_name")
      )
      printClient(client)
    }
    println()
  }

  private fun printClient(client: Client) {
    println(
      "firstName: ${client.firstName}, " +
        "lastName: ${client.lastName}, " +
        "address: ${client.address}, " +
        "phone: ${client.phone}, " +
        "birthDay: ${client.birthDay}, " +
        "roleName: ${client.roleName}"
    )
  }

  override fun getRoles() {
    query = "select * from db.user_role;"
    resultSet = connection.createStatement().executeQuery(query)
    while (resultSet.next()) {
      val userRole = UserRole(
        id = resultSet.getInt("id"),
        name = resultSet.getString("name"),
      )
      println("userRole: ${userRole.name}")
    }
    println()
  }

  override fun getFactories() {
    query = "select * from db.factory;"
    resultSet = connection.createStatement().executeQuery(query)
    while (resultSet.next()) {
      val factory = Factory(
        id = resultSet.getInt("id"),
        name = resultSet.getString("name"),
      )
      println("factory: ${factory.name}")
    }
    println()
  }

  override fun addFactory(factoryName: String) {
    query = "insert into db.factory(name) values ('${factoryName}');"
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
    getFactories()
  }

  override fun updateFactory(oldFactoryName: String, updatedFactoryName: String) {
    query = "UPDATE db.factory SET name='${updatedFactoryName}'  WHERE name='${oldFactoryName}';"
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
    getFactories()
  }

  override fun getPricesList() {
    query = "select * from db.price_list;"
    resultSet = connection.createStatement().executeQuery(query)
    while (resultSet.next()) {
      val priceList = PriceList(
        id = resultSet.getInt("id"),
        price = resultSet.getFloat("price"),
      )
      println("price: ${priceList.price}")
    }
    println()
  }

  override fun addPriceList(price: Float) {
    query = "insert into db.price_list(price) values ('${price}');"
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
    getPricesList()
  }

  override fun addCar(
    color: String,
    factoryName: String,
    price: Float,
    modelName: String,
    yearOfCreation: Date
  ) {
    query =
      "INSERT INTO db.cars (color, factory_id, price_list_id, model_name, year_of_creation)\n" +
        "VALUES (\n" +
        "        '${color}',\n" +
        "        (select id from db.factory where factory.name = '${factoryName}'),\n" +
        "        (select id from db.price_list where price_list.price = ${price}),\n" +
        "        '${modelName}',\n" +
        "        '${yearOfCreation}'\n" +
        "        )"
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
  }

  override fun deleteCar(modelName: String) {
    query = "delete from db.cars where model_name = '$modelName';"
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
    println("Deleted successfully")
  }

  override fun makeOrder(
    carModelName: String,
    clientLogin: String,
    price: Float,
    discount: Float
  ) {
    query = "insert into db.sales(car_id, client_id, date_of_sale, price, discount)\n" +
      "values ((select id from db.cars where cars.model_name = '${carModelName}'),\n" +
      "        (select id from db.clients where clients.login = '${clientLogin}'),\n" +
      "        curdate(),\n" +
      "        (select price_list.price from db.price_list where price_list.price = ${price}),\n" +
      "        ${discount});\n"
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
  }
}