package utils

import java.sql.Date

object Queries {
  const val getClients = "select id,\n" +
    "       first_name,\n" +
    "       last_name,\n" +
    "       address,\n" +
    "       phone,\n" +
    "       birth_day,\n" +
    "       login,\n" +
    "       password,\n" +
    "       (select name from db.user_role where clients.role_id = user_role.id) as role_name\n" +
    "from db.clients;"

  const val getUserRoles = "select * from db.user_role;"

  const val getFactories = "select * from db.factory;"

  const val getPriceList = "select * from db.price_list;"

  const val showCars = "SELECT id,\n" +
    "       model_name,\n" +
    "       color,\n" +
    "       year_of_creation,\n" +
    "       (SELECT name from db.factory WHERE cars.factory_id = factory.id) as 'factory_name',\n" +
    "       (SELECT price from db.price_list WHERE cars.price_list_id = price_list.id) as 'price'\n" +
    "\n" +
    "FROM db.cars "

  fun addFactory(factoryName: String): String {
    return "insert into db.factory(name) values ('${factoryName}');"
  }

  fun updateFactory(oldFactoryName: String, updatedFactoryName: String): String {
    return "UPDATE db.factory SET name='${updatedFactoryName}'  WHERE name='${oldFactoryName}';"
  }

  fun addPriceList(price: Float): String {
    return "insert into db.price_list(price) values ('${price}');"
  }

  fun addCar(
    color: String,
    factoryName: String,
    price: Float,
    modelName: String,
    yearOfCreation: Date
  ): String {
    return "INSERT INTO db.cars (color, factory_id, price_list_id, model_name, year_of_creation)\n" +
      "VALUES (\n" +
      "        '${color}',\n" +
      "        (select id from db.factory where factory.name = '${factoryName}'),\n" +
      "        (select id from db.price_list where price_list.price = ${price}),\n" +
      "        '${modelName}',\n" +
      "        '${yearOfCreation}'\n" +
      "        )"
  }

  fun deleteCar(modelName: String): String {
    return "delete from db.cars where model_name = '$modelName';"
  }

  fun makeOrder(
    carModelName: String,
    clientLogin: String,
    price: Float,
    discount: Float
  ): String {
    return "insert into db.sales(car_id, client_id, date_of_sale, price, discount)\n" +
      "values ((select id from db.cars where cars.model_name = '${carModelName}'),\n" +
      "        (select id from db.clients where clients.login = '${clientLogin}'),\n" +
      "        curdate(),\n" +
      "        (select price_list.price from db.price_list where price_list.price = ${price}),\n" +
      "        ${discount});\n"
  }

  fun showTransactionsOfUser(login: String): String {
    return "select id,\n" +
      "       (select model_name from db.cars where cars.id = sales.car_id) as model_name,\n" +
      "       date_of_sale,\n" +
      "       price,\n" +
      "       discount,\n" +
      "       total_sum\n" +
      "from db.sales where (select id from db.clients where clients.login = '${login}');"
  }

  fun login(login: String, password: String): String {
    return "select exists(select * from db.clients where login = '${login}' and password = '${password}') as count;"
  }

  fun getUserRole(login: String): String {
    return "select role_id from db.clients where login = '${login}';"
  }

  fun register(
    firstName: String,
    lastName: String,
    address: String,
    phone: String,
    birthDay: Date,
    login: String,
    password: String
  ): String {
    return "INSERT INTO db.clients (first_name, last_name, address, phone, birth_day, login, password)\n" +
        "VALUES ('${firstName}', '${lastName}', '${address}', '${phone}', '${birthDay}', '${login}', '${password}' );\n" +
        "\n"

  }
}
