package repositorio;

import model.Localizacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

public class LocalD {
    public static Localizacao findLocalByid(Integer id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "1234");
            ps = conn.prepareStatement("SELECT * FROM locais WHERE id_local = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Integer Localizacao = rs.getInt("id_local");
                String nomeLocal = rs.getString("nome");
                return new Localizacao(Localizacao, nomeLocal);
            } else {
                throw new SQLException("Local não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }
}