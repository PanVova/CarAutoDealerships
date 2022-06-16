package utils

import models.Car
import models.Client
import models.Factory
import models.PriceList
import models.Sales
import models.UserRole
import java.sql.ResultSet

fun clientToDomain(resultSet: ResultSet): Client {
  return Client(
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
}

fun userRoleToDomain(resultSet: ResultSet): UserRole {
  return UserRole(
    id = resultSet.getInt("id"),
    name = resultSet.getString("name"),
  )
}

fun factoryToDomain(resultSet: ResultSet): Factory {
  return Factory(
    id = resultSet.getInt("id"),
    name = resultSet.getString("name"),
  )
}

fun priceListToDomain(resultSet: ResultSet): PriceList {
  return PriceList(
    id = resultSet.getInt("id"),
    price = resultSet.getFloat("price"),
  )
}

fun carToDomain(resultSet: ResultSet): Car {
  return Car(
    id = resultSet.getInt("id"),
    color = resultSet.getString("color"),
    factoryName = resultSet.getString("factory_name"),
    price = resultSet.getFloat("price"),
    modelName = resultSet.getString("model_name"),
    yearOfCreation = resultSet.getDate("year_of_creation")
  )
}

fun saleToDomain(resultSet: ResultSet): Sales {
  return Sales(
    id = resultSet.getInt("id"),
    modelName = resultSet.getString("model_name"),
    dateOfSale = resultSet.getDate("date_of_sale"),
    price = resultSet.getFloat("price"),
    discount = resultSet.getFloat("discount"),
    totalSum = resultSet.getFloat("total_sum"),
  )
}