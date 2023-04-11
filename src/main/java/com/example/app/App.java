package com.example.app;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class App {
    // com.example.ConectorMysql conectorMysql = new com.example.ConectorMysql();
    final static Logger logger = LogManager.getLogger("PruebasLog4j");
    public static void main(String[] args) {

        String consulta="SELECT * from empleados;";

        ConectorMysql conectorMysql = new ConectorMysql();

        Connection connection;
        try{

            //Obtengo la conexión a la base de datos
            connection = conectorMysql.conectarMySQL();

            //Creo una sentencia para la conexión específica
            Statement sentencia=connection.createStatement();

            //Ejecuto la sentencia y recibo un objeto de tipo ResultSet
            ResultSet resultado=sentencia.executeQuery(consulta);

            //Recorremos el bucle y pintamos cada uno de los resultados (lineas del resultado)
            while (resultado.next()){
                System.out.println (resultado.getString (1) + " ////" + resultado.getString(2));
            }

            /*
            int rowsAffected = sentencia.executeUpdate(
                    "INSERT INTO `empleados_departamentos`.`empleados` (`nDIEmp`,`nomEmp`,`sexEmp`,`fecNac`,`fecIncorporacion`,`salEmp`,`comisionE`,`cargoE`,`jefeID`,`codDepto`)" +
                            "VALUES" + "('100.000.001','hola','F','2020/10/10 10:00','2020/10/10 10:00',10000, 10,'Portero',null,1000)"
            );
*/
       //     System.out.println ("FILAS AFECTADAS: "+ rowsAffected);

            int rowsAffected2 = sentencia.executeUpdate(
                    "DELETE FROM `empleados_departamentos`.`empleados` where cargoE='Vendedor'"
            );

            System.out.println ("FILAS AFECTADAS: "+ rowsAffected2);




        }catch (Exception e){

            logger.error("Error en la clase App");
            e.printStackTrace();
        }


        System.out.println("-------------------------------------------------------------------");
       logger.error("Esto es una prueba de error");
       logger.info("Esto es una prueba de info");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////



    }

}
