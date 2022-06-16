package users

import models.Car
import models.Sales
import java.sql.Connection
import java.sql.ResultSet

interface Customer {
  fun showCars(from: Int = -1)
  fun showTransactionsOfUser(login: String)
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

    if (from != -1) query += "limit $from,$limitNumber;"

    resultSet = connection.createStatement().executeQuery(query)

    while (resultSet.next()) {
      val car = Car(
        id = resultSet.getInt("id"),
        color = resultSet.getString("color"),
        factoryName = resultSet.getString("factory_name"),
        price = resultSet.getFloat("price"),
        modelName = resultSet.getString("model_name"),
        yearOfCreation = resultSet.getDate("year_of_creation")
      )
      printCar(car)
    }
    println()
  }

  override fun showTransactionsOfUser(login: String) {
    query =
      "select id,\n" +
        "       (select model_name from db.cars where cars.id = sales.car_id) as model_name,\n" +
        "       date_of_sale,\n" +
        "       price,\n" +
        "       discount,\n" +
        "       total_sum\n" +
        "from db.sales where (select id from db.clients where clients.login = '${login}');"

    resultSet = connection.createStatement().executeQuery(query)
    while (resultSet.next()) {
      val sale = Sales(
        id = resultSet.getInt("id"),
        modelName = resultSet.getString("model_name"),
        dateOfSale = resultSet.getDate("date_of_sale"),
        price = resultSet.getFloat("price"),
        discount = resultSet.getFloat("discount"),
        totalSum = resultSet.getFloat("total_sum"),
      )
      printSale(sale)
    }
    println()
  }

  private fun printSale(sales: Sales) {
    println(
      "model name: ${sales.modelName}, " +
        "date of sale: ${sales.dateOfSale}, " +
        "price: ${sales.price}, " +
        "discount: ${sales.discount}, " +
        "totalSum: ${sales.totalSum}"
    )
  }

  private fun printCar(car: Car) {
    println(
      "name: ${car.modelName}, " +
        "color: ${car.color}, " +
        "factory: ${car.factoryName}, " +
        "price: ${car.price}, " +
        "year of creation: ${car.yearOfCreation}"
    )
  }
}