package users

import models.Role
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet
import java.sql.SQLException

interface User {
  fun login(login: String, password: String): Pair<Int, Role>
  fun register(
    firstName: String,
    lastName: String,
    address: String,
    phone: String,
    birthDay: Date,
    login: String,
    password: String
  ): Pair<Int, Role>
}

class UserImpl(
  private val connection: Connection
) : User {

  private lateinit var resultSet: ResultSet
  private lateinit var query: String

  lateinit var userLogin: String

  override fun login(login: String, password: String): Pair<Int, Role> {
    query =
      "select exists(select * from db.clients where login = '${login}' and password = '${password}') as count;"
    resultSet = connection.createStatement().executeQuery(query)
    resultSet.next()
    val count = resultSet.getInt("count")
    return if (count > 0) {
      println("Login successful")
      userLogin = login
      query = " select role_id from db.clients where login = '${login}';"
      resultSet = connection.createStatement().executeQuery(query)
      resultSet.next()
      val roleId = resultSet.getInt("role_id")
      if (roleId == 1) {
        Pair(0, Role.ADMIN)
      } else {
        Pair(0, Role.CUSTOMER)
      }
    } else {
      println("This user doesn't exist or login or password is wrong")
      Pair(-1, Role.CUSTOMER)
    }
  }

  override fun register(
    firstName: String,
    lastName: String,
    address: String,
    phone: String,
    birthDay: Date,
    login: String,
    password: String
  ): Pair<Int, Role> {
    query =
      "INSERT INTO db.clients (first_name, last_name, address, phone, birth_day, login, password)\n" +
        "VALUES ('${firstName}', '${lastName}', '${address}', '${phone}', '${birthDay}', '${login}', '${password}' );\n" +
        "\n"
    return try {
      connection.createStatement().executeUpdate(query)
      println("Register successful")
      Pair(0, Role.CUSTOMER)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
      Pair(-1, Role.CUSTOMER)
    }
  }
}