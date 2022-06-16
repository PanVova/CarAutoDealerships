package users

import utils.Queries
import utils.carToDomain
import utils.saleToDomain
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
    query = Queries.showCars

    if (from != -1) query += "limit $from,$limitNumber;"

    resultSet = connection.createStatement().executeQuery(query)

    while (resultSet.next()) {
      val car = carToDomain(resultSet)
      car.print()
    }
    println()
  }

  override fun showTransactionsOfUser(login: String) {
    query = Queries.showTransactionsOfUser(login)

    resultSet = connection.createStatement().executeQuery(query)
    while (resultSet.next()) {
      val sale = saleToDomain(resultSet)
      sale.print()
    }
    println()
  }
}