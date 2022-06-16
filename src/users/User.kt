package users

import models.Result
import models.Role
import utils.Queries
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet
import java.sql.SQLException

interface User {
  val userLogin: String
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

  override lateinit var userLogin: String

  override fun login(login: String, password: String): Pair<Int, Role> {
    query = Queries.login(login, password)
    resultSet = connection.createStatement().executeQuery(query)
    resultSet.next()

    val count = resultSet.getInt("count")
    return if (count > 0) {
      println("Login successful")
      userLogin = login
      query = Queries.getUserRole(login)
      resultSet = connection.createStatement().executeQuery(query)
      resultSet.next()

      val roleId = resultSet.getInt("role_id")
      if (roleId == Role.ADMIN.id) Pair(Result.SUCESS.type, Role.ADMIN) // check if user is admin or customer
      else Pair(Result.SUCESS.type, Role.CUSTOMER)
    } else {
      println("This user doesn't exist or login or password is wrong")
      Pair(Result.ERROR.type, Role.CUSTOMER)
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
    query = Queries.register(firstName, lastName, address, phone, birthDay, login, password)
    return try {
      connection.createStatement().executeUpdate(query)
      println("Register successful")
      Pair(Result.SUCESS.type, Role.CUSTOMER)
    } catch (sqlEx: SQLException) {
      println(sqlEx.message)
      Pair(Result.ERROR.type, Role.CUSTOMER)
    }
  }
}