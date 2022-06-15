package users

import models.Car
import java.sql.Connection
import java.sql.ResultSet

interface Customer {
  fun showCars(from: Int = -1)
}

class CustomerImpl(
  private val connection: Connection
) : Customer {

  private lateinit var resultSet: ResultSet
  private lateinit var query: String

  override fun showCars(from: Int) {
    val limitNumber = 10
    query = "SELECT id,\n" +
      "       model_name,\n" +
      "       color,\n" +
      "       year_of_creation,\n" +
      "       (SELECT name from db.factory WHERE cars.factory_id = factory.id) as 'factory_name',\n" +
      "       (SELECT price from db.price_list WHERE cars.price_list_id = price_list.id) as 'price'\n" +
      "\n" +
      "FROM db.cars "


    if (from != -1) {
      query += "limit $from,$limitNumber;"
    }

    resultSet = connection.createStatement().executeQuery(query)
    val cars = mutableListOf<Car>()
    while (resultSet.next()) {
      cars.add(
        Car(
          id = resultSet.getInt("id"),
          color = resultSet.getString("color"),
          factoryName = resultSet.getString("factory_name"),
          price = resultSet.getFloat("price"),
          modelName = resultSet.getString("model_name"),
          yearOfCreation = resultSet.getDate("year_of_creation")
        )
      )
    }
    println(cars.toString())
  }
}