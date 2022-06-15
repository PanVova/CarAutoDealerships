import java.sql.*


object JavaToMySQL {
    // JDBC URL, username and password of MySQL server
    private const val url = "jdbc:mysql://localhost:3306"
    private const val user = "root"
    private const val password = "1234"

    // JDBC variables for opening and managing connection
    private var connection: Connection? = null
    private var statement: Statement? = null
    private var resultSet: ResultSet? = null

    @JvmStatic
    fun main(args: Array<String>) {
        val query = "SHOW DATABASES;"
        try {
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(url, user, password)

            // getting Statement object to execute query
            statement = connection!!.createStatement()

            // executing SELECT query
            resultSet = statement!!.executeQuery(query)
            while (resultSet!!.next()) {
                val databasesName = resultSet!!.getString("Database")
                println(databasesName)
            }
        } catch (sqlEx: SQLException) {
            sqlEx.printStackTrace()
        } finally {
            //close connection ,stmt and resultset here
            try {
                connection?.close()
            } catch (se: SQLException) { /*can't do anything */
            }
            try {
                statement?.close()
            } catch (se: SQLException) { /*can't do anything */
            }
            try {
                resultSet!!.close()
            } catch (se: SQLException) { /*can't do anything */
            }
        }
    }
}