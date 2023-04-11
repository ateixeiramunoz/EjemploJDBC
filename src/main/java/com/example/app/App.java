package com.example.app;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.sql.*;


/**
 * The type App.
 */
public class App {



    /*
    Variable utilizada para hacer logging.
     */
    final static Logger logger = LogManager.getLogger("PruebasLog4j");

    /**
     * Método principal (Entry Point) de la aplicación.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        String consulta="SELECT * from empleados;";

        ConectorMysql conectorMysql = new ConectorMysql();

        Connection connection;
        try{

            /**
             * Obtengo la conexión a la base de datos
             *
             */
            connection = conectorMysql.conectarMySQL();

            //Creo una sentencia para la conexión específica
            Statement sentencia=connection.createStatement();

            //Ejecuto la sentencia y recibo un objeto de tipo ResultSet
            ResultSet resultado=sentencia.executeQuery(consulta);

            //Recorremos el bucle y pintamos cada uno de los resultados (lineas del resultado)
            while (resultado.next()){
                System.out.println (resultado.getString (1) + " ////" + resultado.getString(2));
            }


            /*int rowsAffected = sentencia.executeUpdate(
                    "INSERT INTO `empleados_departamentos`.`empleados` (`nDIEmp`,`nomEmp`,`sexEmp`,`fecNac`,`fecIncorporacion`,`salEmp`,`comisionE`,`cargoE`,`jefeID`,`codDepto`)" +
                            "VALUES" + "('100.000.001','hola','F','2020/10/10 10:00','2020/10/10 10:00',10000, 10,'Portero',null,1000)"
            );


            System.out.println ("FILAS AFECTADAS: "+ rowsAffected);
*/

            int rowsAffected2 = sentencia.executeUpdate(
                    "DELETE FROM `empleados_departamentos`.`empleados` where cargoE='Vendedor'"
            );

            logger.info("FILAS AFECTADAS: "+ rowsAffected2);

            //EMPIEZO A CREAR UN "PROCEDIMIENTO ALMACENADO" O SENTENCIA PRECOMPILADA EN JAVA

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `empleados_departamentos`.`empleados` WHERE nDIEmp = ?");
            preparedStatement.setString(1, "333.333.333");




            ResultSet resultadoPrepared = preparedStatement.executeQuery();
            if( resultadoPrepared.next() ){
                System.out.println ("LINEA ");
                System.out.print(resultadoPrepared.getString("nomEmp"));
                System.out.print(" | ");
                System.out.print(resultadoPrepared.getString("nDIEmp"));
                System.out.print(" | ");
                System.out.println(resultadoPrepared.getDate("fecNac"));
            }

            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO `empleados_departamentos`.`empleados` " +
                            "(`nDIEmp`,`nomEmp`,`sexEmp`, `fecNac`, `fecIncorporacion`,`salEmp`, `comisionE`, `cargoE`, `codDepto`) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            Integer countInserts = 0;
            logger.info("INICIO DEL PROCEDURE");

            sentencia.executeUpdate("LOCK TABLES `empleados_departamentos`.`empleados` WRITE");

            connection.setAutoCommit(false);

            for (int a=1; a<=100000; a++)
            {
                insertStatement.setString(1, String.valueOf(a));
                insertStatement.setString(2, "Pepe");
                insertStatement.setString(3, "M");
                insertStatement.setDate(4, Date.valueOf("2023-01-01"));
                insertStatement.setDate(5, Date.valueOf("2023-01-01"));
                insertStatement.setInt(6, 1000);
                insertStatement.setInt(7, 1000);
                insertStatement.setInt(8, 1000);
                insertStatement.setInt(9, 3500);
                countInserts += insertStatement.executeUpdate();
                if( a % 1000 == 0  )
                {
                    logger.info("IMPRIMIDOS "+ a);
                }
                if( a % 10000 == 0  )
                {
                    logger.info("COMMIT "+ a);
                    connection.commit();
                }


            }
            logger.info("FIN DEL PROCEDURE");
            sentencia.executeQuery("UNLOCK TABLES `empleados_departamentos`.`empleados`");

            connection.commit();



            insertStatement.close();


            resultado.close();
            sentencia.close();
            resultadoPrepared.close();
            preparedStatement.close();
            connection.close();

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
