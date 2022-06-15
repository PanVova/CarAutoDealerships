package users

interface Admin {
  fun getUsers()
  fun promoteUser()
  fun deleteUser()

  fun makeOrder()

  fun addCar()
  fun deleteCar()

  fun showTransactionOfUser(limit: Int = 10)

  fun addFactory()
  fun editFactory()
  fun deleteFactory()

  fun addPriceList()
  fun editPriceList()
  fun deletePriceList()

  fun checkAvailableRoles()
}

class AdminImpl: Admin, Customer {
  override fun getUsers() {
    TODO("Not yet implemented")
  }

  override fun promoteUser() {
    TODO("Not yet implemented")
  }

  override fun deleteUser() {
    TODO("Not yet implemented")
  }

  override fun makeOrder() {
    TODO("Not yet implemented")
  }

  override fun addCar() {
    TODO("Not yet implemented")
    // query = "INSERT INTO db.cars (color, factory_id, price_list_id, model_name, year_of_creation)" +
    //       "VALUES ('Red', 1, 3, 'V7', '1993-06-10');"
    // INSERT CAR
  }

  override fun deleteCar() {
    TODO("Not yet implemented")
  }

  override fun showTransactionOfUser(limit: Int) {
    TODO("Not yet implemented")
  }

  override fun addFactory() {
    TODO("Not yet implemented")
  }

  override fun editFactory() {
    TODO("Not yet implemented")
  }

  override fun deleteFactory() {
    TODO("Not yet implemented")
  }

  override fun addPriceList() {
    TODO("Not yet implemented")
  }

  override fun editPriceList() {
    TODO("Not yet implemented")
  }

  override fun deletePriceList() {
    TODO("Not yet implemented")
  }

  override fun checkAvailableRoles() {
    TODO("Not yet implemented")
  }

  override fun login() {
    TODO("Not yet implemented")
  }

  override fun register() {
    TODO("Not yet implemented")
  }

  override fun showCars() {
    TODO("Not yet implemented")
  }
}