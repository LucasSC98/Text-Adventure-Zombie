package repositorio;

import model.Movimentos;

import java.sql.*;

public class AcoesDAO {
    public static Movimentos findAcaoById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");
            stmt = conn.prepareStatement("SELECT * FROM acoes WHERE id_acao = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Integer id_acao = rs.getInt("id_acao");
                Integer item1_id = rs.getInt("item1_id");
                Integer item2_id = rs.getInt("item2_id");
                String combinacao = rs.getString("descricao_combinacao");
                String nome = rs.getString("nome_acao");
                String descricaoNeg = rs.getString("descricao_neg");
                String descricao = rs.getString("descricao");
                return new Movimentos (id_acao, item1_id, item2_id, combinacao, nome, descricaoNeg, descricao);
            } else {
                throw new SQLException("Ação com ID " + id + " não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
