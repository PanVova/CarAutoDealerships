package users

import models.Car
import java.sql.Connection
import java.sql.ResultSet

interface Customer {
  fun login()
  fun register()
  fun showCars()
}

class CustomerImpl(
  private val connection: Connection
) : Customer {

  private lateinit var resultSet: ResultSet
  private lateinit var query: String

  override fun login() {
    TODO("Not yet implemented")
  }

  override fun register() {
    TODO("Not yet implemented")
  }

  override fun showCars() {
    query = "select * from db.cars;"

    resultSet = connection.createStatement().executeQuery(query)
    val cars = mutableListOf<Car>()
    while (resultSet.next()) {
      cars.add(
        Car(
          id = resultSet.getInt("id"),
          color = resultSet.getString("color"),
          factoryId = resultSet.getInt("factory_id"),
          priceListId = resultSet.getInt("price_list_id"),
          modelName = resultSet.getString("model_name"),
          yearOfCreation = resultSet.getDate("year_of_creation")
        )
      )
    }
    println(cars.toString())
  }
}