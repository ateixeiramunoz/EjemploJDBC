package com.example.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class ConectorMysql {
    final static Logger logger = LogManager.getLogger("HelloWorld");


    // Librería de MySQL
    public String driver = "com.mysql.cj.jdbc.Driver";

    // Nombre de la base de datos
    public String database = "empleados_departamentos";

    // Host
    public String hostname = "localhost";

    // Puerto
    public String port = "3306";

    // Ruta de nuestra base de datos (desactivamos el uso de SSL con "?useSSL=false")
    public String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    // jdbc:mysql://localhost:3306/empleados_departamentos?useSSL=false

    // Nombre de usuario
    public String username = "root";

    // Clave de usuario
    public String password = "password";

    public Connection conectarMySQL() {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {


            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            logger.error("Error de conexion, revisa los parametros de conexion.!");
        }

        return conn;
    }
}
