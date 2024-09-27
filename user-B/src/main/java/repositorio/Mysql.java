package repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    private static Connection connection;


    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/textadventure";
        String user = "root";
        String senha = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, senha
            );
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao connectar");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao importar o driver");
        }
        return null;
    }
}