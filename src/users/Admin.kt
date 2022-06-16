package users

import utils.Queries
import utils.clientToDomain
import utils.factoryToDomain
import utils.priceListToDomain
import utils.userRoleToDomain
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
    resultSet = connection.createStatement().executeQuery(Queries.getClients)
    while (resultSet.next()) {
      val client = clientToDomain(resultSet)
      client.print()
    }
    println()
  }

  override fun getRoles() {
    resultSet = connection.createStatement().executeQuery(Queries.getUserRoles)
    while (resultSet.next()) {
      val userRole = userRoleToDomain(resultSet)
      userRole.print()
    }
    println()
  }

  override fun getFactories() {
    resultSet = connection.createStatement().executeQuery(Queries.getFactories)
    while (resultSet.next()) {
      val factory = factoryToDomain(resultSet)
      factory.print()
    }
    println()
  }

  override fun addFactory(factoryName: String) {
    query = Queries.addFactory(factoryName)
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
    getFactories()
  }

  override fun updateFactory(oldFactoryName: String, updatedFactoryName: String) {
    query = Queries.updateFactory(oldFactoryName, updatedFactoryName)
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
    getFactories()
  }

  override fun getPricesList() {
    resultSet = connection.createStatement().executeQuery(Queries.getPriceList)
    while (resultSet.next()) {
      val priceList = priceListToDomain(resultSet)
      priceList.print()
    }
    println()
  }

  override fun addPriceList(price: Float) {
    query = Queries.addPriceList(price)
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
    query = Queries.addCar(color, factoryName, price, modelName, yearOfCreation)
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
  }

  override fun deleteCar(modelName: String) {
    query = Queries.deleteCar(modelName)
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
  }

  override fun makeOrder(
    carModelName: String,
    clientLogin: String,
    price: Float,
    discount: Float
  ) {
    query = Queries.makeOrder(carModelName, clientLogin, price, discount)
    try {
      connection.createStatement().executeUpdate(query)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
    }
  }
}