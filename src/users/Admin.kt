package users

interface Admin{
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

class AdminImpl {

}